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

@Service
public class ContractService implements IContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    CompanyRepository companyRepository;
    private static String targetCompanyName;

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
        targetCompanyName = companyName2;
        List<Contract> bestMatchingContract = getContractByTwoCompanyNames(companyName1, companyName2);
        List<List<Contract>> sleeves = new ArrayList<List<Contract>>();
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
                for (Contract c : contractsOfFirstLegCompany) {
                    List <Contract> sleeve = new ArrayList<>();
                    sleeve = searchSleeves(c, companyName2);

                    if (!sleeve.isEmpty() ) {
                        sleeve.add(c);
                        Collections.reverse(sleeve);
                        sleeves.add(sleeve);
                    }
                }

            }

            return sleeves;
        }
    }

    private List<Contract> searchSleeves(Contract c, String companyName2) {
        List<Contract> secondLegContracts = new ArrayList<>();
        String secondLegCompanyName = new String(c.getSecondCompanyLeg().getCompanyName());
        secondLegContracts = getContractByFirstCompanyLeg(secondLegCompanyName);
        List<Contract> sleeve = new ArrayList<>();
        if (!secondLegContracts.isEmpty()) {
            for (Contract contract : secondLegContracts) {
                if (contract.getSecondCompanyLeg().getCompanyName().equals(targetCompanyName)) {
                    sleeve.add(contract);
                    return sleeve;
                }
                sleeve.addAll(searchSleeves(contract, contract.getSecondCompanyLeg().getCompanyName()));
                if (!sleeve.isEmpty()) {
                    sleeve.add(contract);
                }

            }
            return sleeve;
        }
        return sleeve;
    }
    @Override
    public List<List<Contract>> calculateSleeves2(String companyName1, String companyName2) throws ContractException {
        List<Contract> l = new ArrayList<>();
        return null;
    }
}
