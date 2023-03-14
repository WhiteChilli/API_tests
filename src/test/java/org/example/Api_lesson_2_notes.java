package org.example;

import dto.OrderDto;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Api_lesson_2_notes {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @Test
    public void createOrderAndCheckStatusCode() {


        OrderDto orderDto = new OrderDto("testname", "123456", "no");
        OrderDto orderDtoRandom = new OrderDto();

        orderDtoRandom.setCustomerName( generateRandomName() );
        orderDtoRandom.setCustomerPhone( generateRandomNumber() );
        orderDtoRandom.setComment( generateRandomComment() );


        given()
                .header("Content-type", "application/json")
                .body(orderDtoRandom)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    public String generateRandomName() {
       return RandomStringUtils.random(5, true, false);
    }

    public String generateRandomNumber() {
        return RandomStringUtils.random(7, false, true);
    }

    public String generateRandomComment() {
        return RandomStringUtils.random(15, true, true);
    }
}

