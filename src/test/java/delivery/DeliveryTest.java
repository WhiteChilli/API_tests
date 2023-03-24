package delivery;

import com.google.gson.Gson;
import dto.OrderDto;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeliveryTest {

    public static String token;

    @BeforeAll
    public static void setup() {
        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();

//        String baseUrl = setupFunctions.getBaseUrl();
//        String username = setupFunctions.getUsername();
//        String password = setupFunctions.getPassword();

        System.out.println("token: " + setupFunctions.getToken());

        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();
    }

    @Test
    public void createOrderTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");

        Gson gson = new Gson();

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    @Test
    public void noCommentTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567");

        Gson gson = new Gson();

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    @Test
    public void noTokenTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567");

        Gson gson = new Gson();

        given()
                .header("Content-type", "application/json")
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}