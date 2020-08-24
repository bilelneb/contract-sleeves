package de.enmacc.task.controller;

import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class ContractController {
    @Autowired
    ContractRepository contractRepository;
    /**
     * Persists the given contract in the database.
     *
     * @param contract The contract to persist
     * @return The persisted contract
     */
    @PostMapping(path = "/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contract addContract(@RequestBody final Contract contract) {

      return  contractRepository.save(contract);

    }

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param aCompany A company
     * @param anotherCompany Another company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @GetMapping(path = "/sleeves")
    public List<List<Contract>> getAllPossibleSleeves(String aCompany, String anotherCompany) {
        Contract c1 = new Contract();
        c1.setCompany(aCompany);
        c1.setAnotherCompany(anotherCompany);
        List<Contract> listContract = new ArrayList<Contract>();
        listContract.add(c1);

        List<List<Contract>> listSleeves = new ArrayList<List<Contract>>();
        listSleeves.add(listContract);
        return listSleeves;


    }

}
