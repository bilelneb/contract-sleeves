package de.enmacc.task.controller;

import de.enmacc.task.exceptions.ContractException;
import de.enmacc.task.model.Company;
import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.CompanyRepository;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.task.services.IContractService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * This entity is a REST Controller for exposing endpoints of the Contract services
 * @author binebli
 * @version 1.0
 */
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
     * @throws TransientPropertyValueException if one of the companies does not exist
     */
    @PostMapping(path = "/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contract addContract(@Valid @RequestBody final Contract contract) {
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
     * @param companyName1 A contract first leg company
     * @param companyName2 A contract second leg company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @GetMapping(path = "/sleeves")
    public List<List<Contract>> getAllPossibleSleeves(@RequestParam("companyName1") String companyName1,@RequestParam("companyName2") String companyName2 ) throws ContractException {
         return   contractService.calculateSleeves(companyName1, companyName2);
    }
    /**
     * Gets all the contracts persisted in the Database
     * @return all the contracts persisted
     *
     */
    @GetMapping(path = "/contracts")
    public List<Contract> getAllContracts() {
        return   contractService.getAllContracts();
    }
    /**
     * Gets all the contracts that verifies that one of the legs is the parameter companyName
     * @param companyName
     * @return all the contracts having one of the legs equal to companyName
     *
     */
    @GetMapping
    @RequestMapping("/contracts/{companyName}")
    public List<Contract> getContractByOneCompanyName(@PathVariable String companyName) {
        return   contractService.getContractByOneCompanyName(companyName);
    }
    /**
     * Gets all the contracts that verifies that both legs are equal companyName1 and companyName2
     * @param companyName1
     * @param companyName2
     * @return all the contracts persisted
     *
     */
    @GetMapping
    @RequestMapping("/contracts/companies")
    public List<Contract> getContractByTwoCompanyNames(@RequestParam("companyName1") String companyName1,@RequestParam("companyName2") String companyName2 ) {
        return   contractService.getContractByTwoCompanyNames(companyName1,companyName2);
    }
    /**
     * Gets all the contracts that verifies that the first leg is equal to companyName1
     * @param companyName1
     * @return all the contracts persisted
     *
     */
    @GetMapping
    @RequestMapping("/contracts/companies/firstLeg")
    List<Contract> getContractByFirstCompanyLeg(@RequestParam("companyName1") String companyName1) {
        return contractService.getContractByFirstCompanyLeg(companyName1);
    }
    /**
     * Gets all the contracts that verifies that the second leg is equal to companyName2
     * @param companyName2
     * @return all the contracts persisted
     *
     */
    @GetMapping
    @RequestMapping("/contracts/companies/secondLeg")
    List<Contract> getContractBySecondCompanyLeg(@RequestParam("companyName2") String companyName2) {
        return contractService.getContractBySecondCompanyLeg(companyName2);
    }
    @GetMapping(path = "/sleevesGraph")
    public List<List<Contract>> getAllPossibleSleevesGraph(@RequestParam("companyName1") String companyName1,@RequestParam("companyName2") String companyName2 ) throws ContractException {
        return   contractService.calculateSleevesGraph(companyName1, companyName2);
    }
}
