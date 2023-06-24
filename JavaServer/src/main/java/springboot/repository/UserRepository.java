package springboot.repository;

import org.springframework.stereotype.Repository;
import springboot.domein.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findByEmail(String email);

    List<User> findAllByCompanyId(Integer companyId);
}
