package de.enmacc.task.model;

import org.springframework.test.util.ReflectionTestUtils;

public class ContractBuilder {
    private Contract model;

    public ContractBuilder() {
        model = new Contract();
    }

    public ContractBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ContractBuilder company1(Company company1) {
        ReflectionTestUtils.setField(model,"firstCompanyLeg", company1);
        return this;
    }

    public ContractBuilder company2(Company company2) {
        ReflectionTestUtils.setField(model,"secondCompanyLeg", company2);
        return this;
    }

    public Contract build() {
        return model;
    }
}
