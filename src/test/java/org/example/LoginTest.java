package org.example;

import dto.Credentials;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LoginTest {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @Test
    public void positiveLoginTest () {

        Credentials credentials = new Credentials();

        credentials.setUsername("string");
        credentials.setPassword("string");

        Response response = given()
                .header("Content-type", "application/json")
                .body(credentials)
                .log()
                .all()
                .post("/login/student")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Assertions.assertNotNull( response.asString() );
    }

    @Test
    public void wrongPassword () {

        Credentials credentials = new Credentials();

        credentials.setUsername("string");
        credentials.setPassword("wrongpass");

        Response response = given()
                .header("Content-type", "application/json")
                .body(credentials)
                .log()
                .all()
                .post("/login/student")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .response();

        Assertions.assertNotNull( response.asString() );
    }


}
