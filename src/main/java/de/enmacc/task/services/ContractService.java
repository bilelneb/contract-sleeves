package de.enmacc.task.services;

import de.enmacc.task.exceptions.ContractException;
import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.CompanyRepository;
import de.enmacc.task.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Service
public class ContractService implements IContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    CompanyRepository companyRepository;
    List<Contract> sleeve = new ArrayList<>();
    List<List<Contract>> sleeves = new ArrayList<>();

    public List<Contract> getContractByOneCompanyName(String companyName) {
        return contractRepository.findByFirstCompanyLeg_CompanyNameOrSecondCompanyLeg_CompanyName(companyName, companyName);
    }

    public List<Contract> getContractByTwoCompanyNames(String companyName1, String companyName2) {
        return contractRepository.findByFirstCompanyLeg_CompanyNameAndSecondCompanyLeg_CompanyName(companyName1, companyName2);
    }

    public List<Contract> getContractByFirstCompanyLeg(String companyName1) {
        return contractRepository.findByFirstCompanyLeg_CompanyName(companyName1);
    }

    public List<Contract> getContractBySecondCompanyLeg(String companyName2) {
        return contractRepository.findBySecondCompanyLeg_CompanyName(companyName2);
    }

    @Override
    public List<List<Contract>> calculateSleeves(String companyName1, String companyName2) throws ContractException {
        List<Contract> bestMatchingContract = getContractByTwoCompanyNames(companyName1, companyName2);
        List<Contract> sleeve = new ArrayList<Contract>();
        List<List<Contract>> sleeves = new ArrayList<List<Contract>>();
        this.sleeve.clear();
        this.sleeves.clear();
        if (!bestMatchingContract.isEmpty()) {
            sleeves.add(bestMatchingContract);
            return sleeves;
        } else {
            List<Contract> contractsOfFirstLegCompany = new ArrayList<Contract>();
            List<Contract> contractsOfSecondLegCompany = new ArrayList<Contract>();
            contractsOfFirstLegCompany = getContractByFirstCompanyLeg(companyName1);
            contractsOfSecondLegCompany = getContractBySecondCompanyLeg(companyName2);
            if (contractsOfSecondLegCompany.isEmpty())
                throw new ContractException("No Sleeves found for both companies" + companyName1 + " and " + companyName2);
            else {
                sleeves = searchSleeves(companyName1, companyName2);
                return sleeves;
            }
        }
    }


    private List<List<Contract>> searchSleeves(String companyName1, String companyName2) {
        List<Contract> contractsOfFirstLegCompany = new ArrayList<Contract>();
        contractsOfFirstLegCompany = getContractByFirstCompanyLeg(companyName1);
        for (Contract contract : contractsOfFirstLegCompany) {
            if (contract.getSecondCompanyLeg().getCompanyName().equals(companyName2)) {
                List<Contract> temporary = new ArrayList<>();
                for (Contract c : sleeve)
                    temporary.add(c);
                temporary.add(contract);
                sleeves.add(temporary);
            } else if (!sleeve.contains(contract)) {
                sleeve.add(contract);
                searchSleeves(contract.getSecondCompanyLeg().getCompanyName(), companyName2);
                sleeve.remove(sleeve.size() - 1);
            }
        }
        return sleeves;
    }

    @Override
    public List<Stack> calculateSleeves2(String node, String targetNode) throws ContractException {
      /*  List<Contract> contractsOfFirstLegCompany = new ArrayList<Contract>();
        List<Contract> contractsOfSecondLegCompany = new ArrayList<Contract>();
// Push to connectionsPath the object that would be passed as the parameter 'node' into the method below
        contractsOfFirstLegCompany = getContractByFirstCompanyLeg(node);
        contractsOfSecondLegCompany = getContractBySecondCompanyLeg(targetNode);
        for (Contract nextNode : contractsOfFirstLegCompany) {
            if (nextNode.getSecondCompanyLeg().getCompanyName().equals(targetNode)) {
                Stack temp = new Stack();
                for (Object node1 : connectionPath)
                    temp.add(node1);
                temp.add(nextNode);
                connectionPaths.add(temp);
            } else if (!connectionPath.contains(nextNode)) {
                connectionPath.push(nextNode);
                calculateSleeves2(nextNode.getSecondCompanyLeg().getCompanyName(), targetNode);
                connectionPath.pop();
            }
        }
*/
        return null;
    }

}
