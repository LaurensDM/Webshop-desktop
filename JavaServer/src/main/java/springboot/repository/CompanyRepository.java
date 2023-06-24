package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.domein.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findById(Integer id);

    // doesn't seem to work
    Company findByCountryCodeAndVatNumber(String countryCode, String vatNumber);


    List<Company> findByCountryCode(String countryCode);

    List<Company> findByVatNumber(String vatNumber);

    
}
