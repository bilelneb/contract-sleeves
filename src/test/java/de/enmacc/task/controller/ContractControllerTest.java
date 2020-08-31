package de.enmacc.task.controller;

import de.enmacc.task.model.Company;
import de.enmacc.task.model.CompanyBuilder;
import de.enmacc.task.model.Contract;
import de.enmacc.task.model.ContractBuilder;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.task.services.ContractService;
import de.enmacc.task.services.IContractService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractControllerTest {
    private MockMvc mockMvc;
    @Autowired
    ContractController contractService;
    @LocalServerPort
    protected int port;
    private ContractController contractServiceMock = new Mockito().mock(ContractController.class);

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        mockMvc = MockMvcBuilders.standaloneSetup(contractService).build();
    }

    @Test
    public void addContract() {
        //language=JSON
        String json = "{\n" +
                "  \"firstCompanyLeg\": \"F\",\n" +
                "  \"secondCompanyLeg\": \"J\"\n" +
                "}";

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("contracts")
        .then()
            .statusCode(HttpStatus.SC_CREATED);
        //@formatter:on
    }

    @Test
    public void getAllPossibleSleeves() {

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .param("companyName1", "A")
            .param("companyName2", "B")
        .when()
            .get("sleeves")
        .then()
            .statusCode(HttpStatus.SC_OK);
        //@formatter:on
    }
    @Test
    public void findAll_ContractsFound_ShouldReturnFoundContractsEntries() throws Exception {
        mockMvc.perform(get("/contracts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].firstCompanyLeg.companyName", is("A")))
                .andExpect(jsonPath("$[0].secondCompanyLeg.companyName", is("B")))
                .andExpect(jsonPath("$[1].firstCompanyLeg.companyName", is("A")))
                .andExpect(jsonPath("$[1].secondCompanyLeg.companyName", is("C")));

        verify(contractServiceMock, times(1)).getAllContracts();
        verifyNoMoreInteractions(contractServiceMock);
    }

}