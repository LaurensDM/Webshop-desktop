package springboot.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import springboot.JavaServerMain;
import springboot.domein.Company;
import springboot.domein.Notification;
import springboot.domein.UserDTO;
import springboot.domein.User;
import springboot.repository.CompanyRepository;
import springboot.repository.NotificationRepository;
import springboot.repository.UserRepository;
import springboot.service.UserService;
import springboot.validator.EmailValidator;
import springboot.validator.NameValidator;
import springboot.validator.PasswordValidator;
import springboot.validator.RoleValidator;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final NotificationRepository notificationRepository;

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, NotificationRepository notificationRepository) {
        super();
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllEmployees(String token) {
        logger.info(String.format("UserServiceImpl -- getAllEmployees(%s)%n", token));
        UserDTO userDTO = decodeUserByToken(token);
        if (!Objects.equals(userDTO.getRole(), "admin")) {
            logger.error(String.format("User with email %s is not an admin; and can not get employee information", userDTO.getEmail()));
            throw new IllegalArgumentException(
                    "\tUser with email " +
                            userDTO.getEmail() +
                            " is not an admin; and can not get employee information"
            );

        }
        if (userDTO.getCompanyId() == null) {
            logger.error(String.format("User with email %s does not have a company id; and can not get employee information", userDTO.getEmail()));
            throw new IllegalArgumentException(
                    "\tUser with email " +
                            userDTO.getEmail() +
                            " does not have a company id; and can not get employee information"
            );
        }
        logger.info(String.format("Admin %s requested all users", userDTO.getEmail()));
        List<User> employees = userRepository.findAllByCompanyId(userDTO.getCompanyId());

        // TODO: Don't return hash and salt. Is this the right way to do this?
        employees.forEach(employee -> {
            employee.setHash(null);
            employee.setSalt(null);
        });

        return employees;
    }

    @Override
    public User getUserById(String id) {
        logger.info(String.format("getUserById(%s)", id));
        User user = userRepository.getById(id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info(String.format("getUserByEmail() -- Getting user by email", email));
        User user = null;
        // TODO: Is this the right way to do this? Maybe I should use orElseThrow?
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
        return user;
    }

    // TODO: this uuid does not work; contains mostly digits and a few letters; how to fix this?
    @Override
    public User updateUser(String id, JSONObject user, String token) throws IllegalAccessException {
        logger.info(String.format("updateUser(%s, %s)", user, id));
        UserDTO userDTO = decodeUserByToken(token);
        User userToUpdate = null;
        userToUpdate = getUserById(id);
        logger.info(String.format("User to update: %s", userToUpdate));

        if (userDTO.getRole().equals("admin")) {
            // ADMIN UPDATE
            logger.info("\tAdmin is updating a user");
            if (userToUpdate.getCompanyId() != userDTO.getCompanyId()) {
                throw new IllegalAccessException(
                        "\tUser with email " +
                                userDTO.getEmail() +
                                " is not allowed to update a user from a different company"
                );
            }
            userToUpdate.setRole(user.get("role") == null ? userToUpdate.getRole() : (String) user.get("role"));
            if (userToUpdate.getRole().equals("unemployed")) {
                userToUpdate.setCompanyId(null);
            }


        } else if (Objects.equals(userDTO.getEmail(), user.get("email"))) {
            // USER UPDATE
            logger.info("\tUser is updating their own information");
            if (userToUpdate.getRole().equals("unemployed")) {
                userToUpdate.setCompanyId(userDTO.getCompanyId());
                userToUpdate.setRole("pending");
            }


        } else {

            logger.error(
                    String.format(
                            "User with email %s is not an admin and is not updating their own information",
                            userDTO.getEmail()
                    )
            );
            throw new IllegalAccessException(
                    "\tUser with email " +
                            userDTO.getEmail() +
                            " is not an admin and is not updating their own information"
            );
        }

        userToUpdate.setName(user.get("name") == null ?
                userToUpdate.getName() : (String) user.get("name"));
        userToUpdate.setFirstName(user.get("firstName") == null ?
                userToUpdate.getFirstName() : (String) user.get("firstName"));
        userToUpdate.setLastName(user.get("lastName") == null ?
                userToUpdate.getLastName() : (String) user.get("lastName"));
        userToUpdate.setEmail(user.get("email") == null ?
                userToUpdate.getEmail() : (String) user.get("email"));


        logger.info(String.format("User updated to: %s", userToUpdate));
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(String id) {

        logger.info(String.format("deleteUser(%s)", id));
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public String login(JSONObject jsonUser) throws IllegalAccessException {

        logger.info("login() -- a user logged in!");
        // Check if user exists
        User user;
        String email = (String) jsonUser.get("email");
        String password = (String) jsonUser.get("password");
        try {
            user = userRepository.findByEmail(email);
            logger.info(String.format("%n%s", user));
        } catch (Exception e) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }

        // Hashing Part
        String salt = user.getSalt();
        String encodedHashedString = getEncodedHashedString(password, salt);

        // Check if hashed password is the same as the one in the database
        if (!Objects.equals(user.getHash(), encodedHashedString)) {
            throw new IllegalAccessException("Password incorrect");
        }

        // JWT Part
        String token = generateToken(user);
        return token;
    }

    // TODO: split this function up (maye some of it in the CompanyServiceImpl?)
    @Override
    public String register(JSONObject jsonUser) {
        logger.info(String.format("register(%s)", jsonUser));
        String name = (String) jsonUser.get("name");
        String email = (String) jsonUser.get("email");
        String password = (String) jsonUser.get("password");
        String companyVAT = (String) jsonUser.get("companyVAT");

        // Check if user already exists in database
        checkIfEmailExistsInDatabase(email);

        // check if vat number is valid with api
        JSONObject VATAPIResponse = CompanyServiceImpl.callVatApi(companyVAT);
        logger.info(String.format("Api returned valid company: %s", VATAPIResponse.get("valid")));
        if ((Boolean) VATAPIResponse.get("valid")) {
            // check if company is already registered in database
            String countryCodeFromAPI = (String) VATAPIResponse.get("countryCode");
            String vatNumberFromAPI = (String) VATAPIResponse.get("vatNumber");

            logger.info(String.format("Checking if company with id %s already exists in database", companyVAT));
            List<Company> companiesWithIdenticalVATNumber = companyRepository.findByVatNumber(vatNumberFromAPI);

            // TODO: Is there a way to call the repository and check for the countrycode and vatNumber at the same time?
            companiesWithIdenticalVATNumber.forEach(company -> {
                if (company.getCountryCode().equals(countryCodeFromAPI)) {
                    logger.error(String.format("Company with id %s already exists in database", companyVAT));
                    throw new DuplicateKeyException(
                            String.format(
                                    "Company with countrycode %s and VAT number %s already exists in our database. " +
                                            "Contact support if you are the owner of this company",
                                    countryCodeFromAPI,
                                    vatNumberFromAPI
                            ));
                }
            });
        } else {
            throw new IllegalArgumentException("Company with VAT number " + companyVAT + " does not exist");
        }

        // check if name is a valid name
        if (!NameValidator.nameIsValid(name)) {
            logger.error(String.format("Name %s is not a valid name", name));
            throw new IllegalArgumentException(String.format("Name %s is not a valid name", name));
        }

        // check if email is a valid email
        if (!EmailValidator.emailIsValid(email)) {
            logger.error(String.format("Email %s is not a valid email", email));
            throw new IllegalArgumentException(String.format("Email %s is not a valid email", email));
        }

        // check if password is a valid password
        if (!PasswordValidator.passwordIsValid(password)) {

            logger.error("Password is not strong enough");
            throw new IllegalArgumentException(
                    "Password is not strong enough, make sure it contains at least 8 characters"
            );
        }

        // TODO: create transaction that adds company and user to database
        Company newCompany;
        try {
            // Create company
            String countryCode = (String) VATAPIResponse.get("countryCode");
            String vatNumber = (String) VATAPIResponse.get("vatNumber");
            String companyName = (String) VATAPIResponse.get("name");

            // TODO: [far out] create an Address object in database?
            JSONObject address = (JSONObject) VATAPIResponse.get("address");
            String street = (String) address.get("street");
            String streetNumber = (String) address.get("number");
            String zipCode = (String) address.get("zip_code");
            String city = (String) address.get("city");
            String country = (String) address.get("country");
            newCompany = new Company(companyName, countryCode, vatNumber, street, streetNumber, zipCode, city, country);

            User user = userRepository.findByEmail(email);
            String userId = user.getId();
            Integer companyId = user.getCompanyId();

            /*
            Notification notification = new Notification("Welcome to the Company!", userId, companyId);
            notificationRepository.save(notification);
             */
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Something went wrong while receiving the company data from an external source. " +
                            "Please try again later"
            );
        }
        logger.info(String.format("Company created: %s", newCompany));
        Company savedCompanyFromDatabase = companyRepository.save(newCompany);
        logger.info(String.format("Company saved: %s", savedCompanyFromDatabase));

        String salt = generateRandomSalt();
        String encodedHashedString = getEncodedHashedString(password, salt);
        // Create user
        User user = new User(name, "", "", email, "admin", savedCompanyFromDatabase.getId(), encodedHashedString, salt);

        logger.info(String.format("User created: %s", user));
        User savedUserFromDatabase = userRepository.save(user);

        //notification
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        Notification notification = new Notification("Welcome to the Company!", formattedDate, savedUserFromDatabase.getId());
        notificationRepository.save(notification);

        logger.info(String.format("User saved: %s", savedUserFromDatabase));
        return generateToken(savedUserFromDatabase);
    }

    @Override
    public User createUserAndAddToCompany(JSONObject jsonUser, String token) throws IllegalAccessException {

        User parentUser = getUserByToken(token);
        if (!parentUser.getRole().equals("admin")) {
            throw new IllegalAccessException("Only admins can create new users for their company");
        }

        String name = (String) jsonUser.get("name");
        String firstName = (String) jsonUser.get("firstName");
        String lastName = (String) jsonUser.get("lastName");
        String email = (String) jsonUser.get("email");

        if (!NameValidator.nameIsValid(name)) {
            throw new IllegalArgumentException("Name is empty or too long");
        }
        if (!NameValidator.nameIsValid(firstName)) {
            throw new IllegalArgumentException("First name is empty or too long");
        }
        if (!NameValidator.nameIsValid(lastName)) {
            throw new IllegalArgumentException("Last name is empty or too long");
        }
        if (!EmailValidator.emailIsValid(email)) {
            throw new IllegalArgumentException("Email address is not valid");
        }

        checkIfEmailExistsInDatabase(email);

        String password = (String) jsonUser.get("password");
        String role = (String) jsonUser.get("role");

        String salt = generateRandomSalt();
        String hash = getEncodedHashedString(password, salt);

        if (!PasswordValidator.passwordIsValid(password)) {
            throw new IllegalArgumentException("Password is not strong enough");
        }
        if (!RoleValidator.roleIsValid(role)) {
            throw new IllegalArgumentException("Role is not valid");
        }


        String street = (String) jsonUser.get("street");
        String streetNumber = (String) jsonUser.get("streetNumber");
        String zipCode = (String) jsonUser.get("zipCode");
        String city = (String) jsonUser.get("city");
        String country = (String) jsonUser.get("country");

        Integer companyId = parentUser.getCompanyId();

        User newUser = new User(
                name, firstName, lastName, email,
                role, companyId,
                hash, salt,
                street, streetNumber, zipCode, city, country
        );


        return userRepository.save(newUser);
    }

    private static String getEncodedHashedString(String password, String salt) {
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);

        String encodedHashedString = Base64.getEncoder().encodeToString(hashedBytes);
        return encodedHashedString;
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDTO decodeUserByToken(String token) throws io.jsonwebtoken.SignatureException {

        logger.info(String.format(" decodeUserByToken(%s)%n", token));
        String tokenWithoutBearer = token.split(" ")[1];
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JavaServerMain.appProps.getProperty("jwt.secret"))
                    .parseClaimsJws(tokenWithoutBearer)
                    .getBody();
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new io.jsonwebtoken.SignatureException("Token with invalid signature");
        }

        logger.info(String.format("\tDecoded token and found user with email: %s and role: %s%n", claims.get("email"), claims.get("role")));
        UserDTO userDTO = new UserDTO(
                (String) claims.get("name"),
                (String) claims.get("firstName"),
                (String) claims.get("lastName"),
                (String) claims.get("email"),
                (String) claims.get("role"),
                (Integer) claims.get("companyId"));
        return userDTO;
    }

    public String generateToken(User u) {
        logger.info(String.format(" Generating token for user %s%n", u.getEmail()));
        Claims claims = Jwts.claims().setSubject(u.getEmail());
        claims.put("name", u.getName());
        claims.put("firstName", u.getFirstName());
        claims.put("lastName", u.getLastName());
        claims.put("email", u.getEmail());
        claims.put("role", u.getRole());
        claims.put("companyId", u.getCompanyId());
//        claims.put("iat", new Date(System.currentTimeMillis()));
//        claims.put("exp", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)); // 10h
        Date iat = new Date();
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date());               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 10);      // adds one hour
        Date exp = cal.getTime();

        return Jwts.builder().setClaims(claims).setIssuedAt(iat).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).signWith(SignatureAlgorithm.HS256, JavaServerMain.appProps.getProperty("jwt.secret")).compact();
    }

    public User getUserByToken(String token) {
        logger.info(String.format(" Getting user by token%n"));
        UserDTO userDTO = decodeUserByToken(token);
        return getUserByEmail(userDTO.getEmail());
    }

    public static String generateRandomSalt() {
        logger.info(String.format(" Generating random salt%n"));
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[128];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private void checkIfEmailExistsInDatabase(String email) {

        logger.info(String.format("\tChecking if user with email %s exists%n", email));
        if (userRepository.findByEmail(email) != null) {
            logger.error(String.format(" User with email %s already exists%n", email));
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }
    }

}
