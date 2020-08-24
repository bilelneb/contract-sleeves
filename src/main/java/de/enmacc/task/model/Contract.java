package de.enmacc.task.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    private String company;
    private String anotherCompany;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAnotherCompany() {
        return anotherCompany;
    }

    public void setAnotherCompany(String anotherCompany) {
        this.anotherCompany = anotherCompany;
    }
}
