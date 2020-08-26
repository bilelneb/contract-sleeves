package de.enmacc.task.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_name1",referencedColumnName = "companyName")
    private Company firstCompanyLeg;
    @ManyToOne
    @JoinColumn(name = "company_name2",referencedColumnName = "companyName")
    private Company secondCompanyLeg;

    public Contract() {
    }

    public Contract(Company firstCompanyLeg, Company secondCompanyLeg) {
        this.firstCompanyLeg = firstCompanyLeg;
        this.secondCompanyLeg = secondCompanyLeg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
