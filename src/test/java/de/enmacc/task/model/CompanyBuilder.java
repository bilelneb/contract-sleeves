package de.enmacc.task.model;

import org.springframework.test.util.ReflectionTestUtils;

public class CompanyBuilder {
    private Company model;

    public CompanyBuilder() {
        model = new Company();
    }

    public CompanyBuilder companyId(String companyName) {
        ReflectionTestUtils.setField(model, "companyName", companyName);
        return this;
    }

    public CompanyBuilder companyDescription(String companyDescription) {
        ReflectionTestUtils.setField(model,"companyDescription", companyDescription);
        return this;
    }

    public Company build() {
        return model;
    }
}
