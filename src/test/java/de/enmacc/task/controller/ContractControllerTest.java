package de.enmacc.task.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void beforeTest() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void addContract() {
        //language=JSON
        String json = "{\n" +
                "  \"aCompany\": \"A\",\n" +
                "  \"anotherCompany\": \"B\"\n" +
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
            .param("aCompany", "A")
            .param("anotherCompany", "B")
        .when()
            .get("sleeves")
        .then()
            .statusCode(HttpStatus.SC_OK);
        //@formatter:on

    }
}