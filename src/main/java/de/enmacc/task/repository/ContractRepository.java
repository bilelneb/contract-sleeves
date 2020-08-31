package de.enmacc.task.repository;

import de.enmacc.task.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByFirstCompanyLeg_CompanyNameOrSecondCompanyLeg_CompanyName(String companyName1, String companyName2);
    List<Contract> findByFirstCompanyLeg_CompanyNameAndSecondCompanyLeg_CompanyName(String companyName1,String companyName2);
    List<Contract> findByFirstCompanyLeg_CompanyName(String companyName1);
    List<Contract> findBySecondCompanyLeg_CompanyName(String companyName2);

}
