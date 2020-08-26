package de.enmacc.task.controller;

import de.enmacc.task.exceptions.ContractException;
import de.enmacc.task.model.Company;
import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.CompanyRepository;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.task.services.ContractService;
import de.enmacc.task.services.IContractService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ContractController {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    IContractService contractService;
    /**
     * Persists the given contract in the database.
     *
     * @param contract The contract to persist
     * @return The persisted contract
     */
    @PostMapping(path = "/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contract addContract(@RequestBody final Contract contract) {
        try {
            return  contractRepository.save(contract);
        }
        catch (InvalidDataAccessApiUsageException exp){

            throw new TransientPropertyValueException
                    ("One or both of the Companies that you want to save are not found ",
                            Company.class.getName(),Contract.class.getName(),"company name");
        }
    }

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param companyName1 A company
     * @param companyName2 Another company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @GetMapping(path = "/sleeves")
    public List<List<Contract>> getAllPossibleSleeves(@RequestParam("companyName1") String companyName1,@RequestParam("companyName2") String companyName2 ) throws ContractException {
         return   contractService.calculateSleeves(companyName1, companyName2);
    }
    @GetMapping(path = "/contracts")
    public List<Contract> getAllContracts() {
        return   contractRepository.findAll();
    }
    @GetMapping
    @RequestMapping("/contracts/{companyName}")
    public List<Contract> getContractByOneCompanyName(@PathVariable String companyName) {
        //    Company company1 = companyRepository.getOne(companyName);
        return   contractService.getContractByOneCompanyName(companyName);
    }
    @GetMapping
    @RequestMapping("/contracts/companies")
    public List<Contract> getContractByTwoCompanyNames(@RequestParam("companyName1") String companyName1,@RequestParam("companyName2") String companyName2 ) {
        return   contractService.getContractByTwoCompanyNames(companyName1,companyName2);
    }
    @GetMapping
    @RequestMapping("/contracts/companies/firstLeg")
    List<Contract> getContractByFirstCompanyLeg(@RequestParam("companyName1") String companyName1) {
        return contractService.getContractByFirstCompanyLeg(companyName1);
    }
    @GetMapping
    @RequestMapping("/contracts/companies/secondLeg")
    List<Contract> getContractBySecondCompanyLeg(@RequestParam("companyName2") String companyName2) {
        return contractService.getContractBySecondCompanyLeg(companyName2);
    }
    @GetMapping(path = "/sleeves2")
    public List<Stack> getAllPossibleSleeves2(@RequestParam("companyName1") String companyName1, @RequestParam("companyName2") String companyName2 ) throws ContractException {
        return   contractService.calculateSleeves2(companyName1, companyName2);
    }
}
