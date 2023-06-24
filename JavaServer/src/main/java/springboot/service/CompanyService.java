package springboot.service;

import javassist.NotFoundException;
import springboot.domein.Company;

public interface CompanyService {
    Company getCompanyById(
            Integer id,
            String token
    ) throws NotFoundException, IllegalAccessException;

}
