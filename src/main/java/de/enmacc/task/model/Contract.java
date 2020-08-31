package de.enmacc.task.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This entity is a POJO for persisting the Contract object
 * in a relational Database
 * @author binebli
 * @version 1.0
 */
@Entity
public class Contract {
    /**
     * The id is a field used as a primary key for the Contract entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * The firstCompanyLeg defines the relation between a contract and a company
     */
    @NotNull(message = "The first Company Leg is Mandatory")
    @ManyToOne
    @JoinColumn(name = "company_name1",referencedColumnName = "companyName")
    private  Company firstCompanyLeg;
    /**
     * The secondCompanyLeg defines the relation between a contract and a company
     */
    @NotNull(message = "The second Company Leg is Mandatory")
    @ManyToOne
    @JoinColumn(name = "company_name2",referencedColumnName = "companyName")
    private  Company secondCompanyLeg;

    public Contract() {
    }

    public Contract(Company firstCompanyLeg, Company secondCompanyLeg) {
        this.firstCompanyLeg = firstCompanyLeg;
        this.secondCompanyLeg = secondCompanyLeg;
    }

    public Company getFirstCompanyLeg() {
        return firstCompanyLeg;
    }

    public void setFirstCompanyLeg(Company firstCompanyLeg) {
        this.firstCompanyLeg = firstCompanyLeg;
    }

    public Company getSecondCompanyLeg() {
        return secondCompanyLeg;
    }

    public void setSecondCompanyLeg(Company secondCompanyLeg) {
        this.secondCompanyLeg = secondCompanyLeg;
    }
}
