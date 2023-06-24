package springboot.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private static final Logger logger = LogManager.getLogger(CompanyController.class);
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        super();
        this.companyService = companyService;
    }
    // TODO: get company info by id ->
    //  user must have companyID in token and role employee, admin or warehouseman

    @GetMapping("{id}")
    public ResponseEntity<Object> getCompanyById(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
        ResponseEntity responseEntry;
        try {
            response.put("company", this.companyService.getCompanyById(id, token));
            logger.info(String.format("Company with id %d found, status set to OK", id));
            status = HttpStatus.OK;
        } catch (IllegalAccessException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        } finally {
            responseEntry = ResponseEntity
                    .status(status)
                    .body(response);
        }

        return responseEntry;
    }
}
