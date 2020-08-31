package de.enmacc.task.services;

import de.enmacc.task.Graph;
import de.enmacc.task.exceptions.ContractException;
import de.enmacc.task.model.Company;
import de.enmacc.task.model.Contract;
import de.enmacc.task.repository.CompanyRepository;
import de.enmacc.task.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the service layer for Contracts where implemented the business
 * logic
 *
 * @author binebli
 */
@Service
public class ContractService implements IContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    CompanyRepository companyRepository;

    List<Contract> sleeve = new ArrayList<>();
    List<List<Contract>> sleeves = new ArrayList<>();

    /**
     * Persists the given contract in the database.
     *
     * @param companyName The contract to persist
     * @return The persisted contract
     */
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
    public List<List<Contract>> calculateSleevesGraph(String companyName1, String companyName2) {
        Graph<Company> g = new Graph<Company>();
        List<Contract> l = contractRepository.findAll();
        for (Contract c : l) {
            g.addEdge(c.getFirstCompanyLeg(), c.getSecondCompanyLeg(), false);
        }
        System.out.println("Graph:\n"
                + g.toString());

        // gives the no of vertices in the graph.
        g.getVertexCount();

        // gives the no of edges in the graph.
        g.getEdgesCount(false);

        // tells whether the edge is present or not.
        g.hasEdge(new Company("A"), new Company("B"));

        // tells whether vertex is present or not
        g.hasVertex(new Company("5"));

        return null;
    }
 /*   public void printAllPaths(Company  companySource, Company companyTarget,int vertices,Graph g)
    {
        boolean[] isVisited = new boolean[vertices];
        ArrayList<Company> pathList = new ArrayList<>();

        // add source to path[]
//        pathList.add();

        // Call recursive utility
        printAllPathsUtil(companySource, companyTarget, isVisited, pathList, g);
    }

    private void printAllPathsUtil(Company companySource, Company companyTarget, boolean[] isVisited, ArrayList<Company> pathList,Graph g) {
        if (companySource.getCompanyName().equals(companyTarget.getCompanyName())) {
            System.out.println(pathList);
            // if match found then no need to traverse more till depth
            return;
        }

        // Mark the current node
       // isVisited[] = true;

        // Recur for all the vertices
        // adjacent to current vertex
        for (Object company : g.getAdjacent(companySource)) {
            if (!isVisited[i]) {
                // store current node
                // in path[]
                pathList.add(company);
                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                pathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }
*/

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param companyName1 A contract first leg company
     * @param companyName2 A contract second leg company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @Override
    public List<List<Contract>> calculateSleeves(String companyName1, String companyName2) throws ContractException {
        /* Gets the best matching contract between two companies
        if found it will be returned no need to find sleeves*/
        List<Contract> bestMatchingContract = getContractByTwoCompanyNames(companyName1, companyName2);
        List<List<Contract>> sleeves = new ArrayList<List<Contract>>();
        /**Clear the two lists for every execution of this method*/
        this.sleeve.clear();
        this.sleeves.clear();
        if (!bestMatchingContract.isEmpty()) {
            sleeves.add(bestMatchingContract);
            return sleeves;
        } else {

            List<Contract> contractsOfSecondLegCompany = new ArrayList<Contract>();
            /** Trying to see if the second company has already al least on contract to check if we have the chance to find
             * a sleeve or not
             */
            contractsOfSecondLegCompany = getContractBySecondCompanyLeg(companyName2);
            if (contractsOfSecondLegCompany.isEmpty())
                throw new ContractException("No Sleeves found for both companies" + companyName1 + " and " + companyName2);
            else {
                sleeves = searchSleeves(companyName1, companyName2);
                if (sleeves.isEmpty())
                    throw new ContractException("No Sleeves found for both companies" + companyName1 + " and " + companyName2);
                return sleeves;
            }
        }
    }

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param companyName1 A contract first leg company
     * @param companyName2 A contract second leg company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */

    private List<List<Contract>> searchSleeves(String companyName1, String companyName2) {
        /* Get the contract list by the first company name:
        The first Vertex for graph traversing
         */
        List<Contract> contractsOfFirstLegCompany = new ArrayList<Contract>();
        contractsOfFirstLegCompany = getContractByFirstCompanyLeg(companyName1);
        /* Iterate over all the adjacent of the root node (The company source)*/
        for (Contract contract : contractsOfFirstLegCompany) {
            /* Check if we found the target company or the destination*/
            if (contract.getSecondCompanyLeg().getCompanyName().equals(companyName2)) {

                /* Iterate over the list sleeve where we added recursively the path to
                 * a temporary list to be add to the list of Sleeves */
                List<Contract> temporary = new ArrayList<>();
                for (Contract c : sleeve)
                    temporary.add(c);
                /* Add the last node (target) to the list to be returned*/
                temporary.add(contract);
                sleeves.add(temporary);
                /* To check if the vertex is visited or not */
            } else if (!sleeve.contains(contract)) {
                /* this list is a possible sleeve candidate if it will
                 * find a target node as implemented above it will be returned to the sleeves list
                 * else it will be emptied */
                sleeve.add(contract);
                searchSleeves(contract.getSecondCompanyLeg().getCompanyName(), companyName2);
                /* If we reach this it means that the we did not find the target node in this path so we need
                 * to empty the list*/
                sleeve.remove(sleeve.size() - 1);
            }
        }
        return sleeves;
    }
    public List<Contract> getAllContracts() {
        return   contractRepository.findAll();
    }
}
