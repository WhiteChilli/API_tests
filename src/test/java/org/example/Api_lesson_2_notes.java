package org.example;

import com.google.gson.Gson;
import dto.OrderDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
//        OrderDto orderDtoRandom = new OrderDto();

        orderDto.setCustomerName( generateRandomName() );
        orderDto.setCustomerPhone( generateRandomNumber() );
        orderDto.setComment( generateRandomComment() );

        Gson gson = new Gson();

        Response response = given()
                .header("Content-type", "application/json")
                .body(orderDto)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .extract()
                .response();

        OrderDto orderDtoReceived = gson.fromJson(response.asString(), OrderDto.class);

        assertEquals(orderDto.getCustomerName(), orderDtoReceived.getCustomerName());
        assertEquals(orderDto.getCustomerPhone(), orderDtoReceived.getCustomerPhone());
        assertEquals(orderDto.getComment(), orderDtoReceived.getComment());

        Assertions.assertNotNull( orderDtoReceived.getId() );
        Assertions.assertNull( orderDtoReceived.getStatus() );

        assertAll(
                "Grouped Assertions of User",
                () -> assertEquals("noo", orderDtoReceived.getComment(), "1st Assert"),
                () -> assertEquals("testnamee", orderDtoReceived.getCustomerName(), "2nd Assert")
        );




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

