package de.enmacc.task.services;


import de.enmacc.task.exceptions.ContractException;
import de.enmacc.task.model.Contract;

import java.util.List;

public interface IContractService {
   List<List<Contract>> calculateSleeves(String companyName1, String companyName2) throws ContractException;
   List<Contract> getContractByOneCompanyName( String companyName);
    List<Contract> getContractByTwoCompanyNames( String companyName1, String companyName2 );
    List<Contract> getContractByFirstCompanyLeg( String companyName1);
    List<Contract> getContractBySecondCompanyLeg(String companyName2);
    List<List<Contract>> calculateSleeves2(String companyName1, String companyName2) throws ContractException;

}
