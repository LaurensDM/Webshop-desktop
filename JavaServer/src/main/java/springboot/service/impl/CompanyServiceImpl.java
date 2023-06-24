package springboot.service.impl;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import springboot.controller.CompanyController;
import springboot.domein.ApiCall;
import springboot.domein.Company;
import springboot.domein.UserDTO;
import springboot.repository.CompanyRepository;
import springboot.service.CompanyService;

import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger logger = LogManager.getLogger(CompanyServiceImpl.class);
    private final CompanyRepository companyRepository;
//    private final UserService userService;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super();
        this.companyRepository = companyRepository;
        // TODO: maybe import appProps file here; might have to be use to validate users (token)
    }

    @Override
    public Company getCompanyById(Integer id, String token) throws NotFoundException, IllegalAccessException {
        logger.info(String.format("CompanyServiceImpl -- getCompanyById -- id: %d%n", id));
        UserDTO userDTO = null;
        try {
            userDTO = UserServiceImpl.decodeUserByToken(token);
        } catch (Exception e) {
            throw new IllegalAccessException("Failed to decode user from token");
        }
        logger.info(String.format("User %s with role %s and companyId %d is requesting company with id %d", userDTO.getEmail(), userDTO.getRole(), userDTO.getCompanyId(), id));
        if (userDTO.getCompanyId() != id) {
            throw new IllegalAccessException("User is not allowed to access this company");
        }
        Company company = companyRepository.findById(id);
        if (company == null) {
            throw new NotFoundException("Company not found");
        }
        return company;
    }

    // ------------------
    public static JSONObject callVatApi(String companyVAT) {
        JSONObject VATAPIResponse;
        try {
            VATAPIResponse = ApiCall.get(
                    String.format("https://controleerbtwnummer.eu/api/validate/%s.json", companyVAT)
            );
        } catch (IOException e) {
            throw new InternalError("Failed to call verification API, please try again later");
        }
        return VATAPIResponse;
    }


}
