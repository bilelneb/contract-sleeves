package de.enmacc.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This entity is a POJO for persisting the Company object
 * in a relational Database
 * @author binebli
 * @version 1.0
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Company {
    /**
     * The companyName is a field describing the name of the company
     */
    @Id
    private String companyName;
    /**
     * The companyDescription is a field for a short description of the company
     */
    @NotNull(message = "The company description is mandatory")
    private String companyDescription;

    public Company() {
    }

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyName.equals(company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
