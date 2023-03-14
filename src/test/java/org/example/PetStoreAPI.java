package org.example;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class PetStoreAPI {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5})
    public void correctIDsCheckOrderPlaced(int id) {
        String responseString = given().
                log().
                all().
                when().
                get("/store/order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                asString();
        Assertions.assertTrue(responseString.contains("placed"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    public void correctIDsParamTest(int orderId) {

        given().
                log().
                all().
                when().
                get("/store/order/{orderId}", orderId).
                then().
                statusCode(HttpStatus.SC_OK);

    }

    @ParameterizedTest
    @ValueSource(ints = {11, 0})
    public void wrongIDsParamTest(int orderId) {

        given().
                log().
                all().
                when().
                get("/store/order/{orderId}", orderId).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

    }
}