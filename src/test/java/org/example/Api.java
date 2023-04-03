package org.example;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;


public class Api {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @Test
    public void getExistingOrderByIDsWithinRange() {

        given().
                log().
                all().
                when().
                get("http://51.250.6.164:8080/test-orders/5").
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getOrderByIDsOutOfRange() {

        given().
                when().
                get("http://51.250.6.164:8080/test-orders/15").
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(ints = {18, 40, 59})
    public void checkTestOrderUnsuccessfulIDsOutOfRange(int id) {

        given().
                log().
                all().
                when().
                get("/test-orders/{id}", id).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 5})
    public void checkTestOrderSuccessfulIDsWithinRange(int id) {

        given().
                log().
                all().
                when().
                get("/test-orders/{id}", id).
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void simplePositiveTestAndExtractBodyAsString() {

        String responseString = given().
                log().
                all().
                when().
                get("/test-orders/5").
                then().
                log().
                all().
                statusCode(200).
                and().
                extract().
                asString();

        Assertions.assertTrue(responseString.contains("OPEN"));
    }

    @Test
    public void simplePositiveTestAndExtractParameterFromBody() {

        int responseId = given().
                log().
                all().
                when().
                get("/test-orders/5").
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("id");

        Assertions.assertTrue(responseId > 0);
    }

    String order = "{\"customerName\":\"name\",\"customerPhone\":\"123456\",\"comment\":\"comment\"}";

    @Test
    public void createOrderAndCheckStatusCodeOK() {

        given()
                .header("Content-type", "application/json")
                .body(order)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void checkUnsuccessfulOrderCreationWithoutHeader() {

        given()
                .body(order)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);

    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    public void checkOrderStatusWithIDsWithinRange(int id) {
        String responseString = given().
                log().
                all().
                when().
                get("/test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                asString();
        Assertions.assertTrue(responseString.contains("OPEN"));
    }

}
