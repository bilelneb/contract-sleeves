package de.enmacc.task.controller;

import de.enmacc.task.model.Company;
import de.enmacc.task.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This entity is a REST Controller for exposing endpoints of the Company services
 * @author binebli
 * @version 1.0
 */
@RestController
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;
    /**
     * persists a companies in the the database.
     *
     * @param company The company to persist
     * @return The persisted company
     */
    @PostMapping(path = "/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@Valid @RequestBody final Company company) {
      return  companyRepository.save(company);
    }
    /**
     * Gets all the companies from the database.
     *
     * @return all The persisted companies
     */
    @GetMapping(path = "/companies")
    public List<Company> getAllCompanies() {
     return   companyRepository.findAll();
    }
    /**
     * Gets a company by its name from the database.
     * @param companyName
     * @return The persisted company
     */
    @GetMapping
    @RequestMapping ("/companies/{companyName}")
    public Company findByCompanyName (@PathVariable String companyName){
        return   companyRepository.getOne(companyName);
    }

}
