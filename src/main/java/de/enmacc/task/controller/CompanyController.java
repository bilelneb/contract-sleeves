package de.enmacc.task.controller;

import de.enmacc.task.model.Company;
import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.CompanyRepository;
import de.enmacc.task.repository.ContractRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @PostMapping(path = "/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody final Company company) {
      return  companyRepository.save(company);
    }

    @GetMapping(path = "/companies")
    public List<Company> getAllCompanies() {
     return   companyRepository.findAll();
    }
    @GetMapping
    @RequestMapping ("/companies/{companyName}")
    public Company findByCompanyName (@PathVariable String companyName){
        return   companyRepository.getOne(companyName);
    }

}
