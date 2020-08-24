package de.enmacc.task.repository;

import de.enmacc.task.model.Contract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends CrudRepository<Contract, UUID> {
    List<Contract> findAll();
}
