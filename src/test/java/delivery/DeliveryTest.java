package delivery;

import com.google.gson.Gson;
import dto.Courier;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeliveryTest {

    public static String token;

    @BeforeAll
    public static void setup() {
        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();

        System.out.println("token: " + setupFunctions.getToken());

        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();
    }

    @Test
    public void createOrderTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");

        Gson gson = new Gson();

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
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
    public void createOrderWithNoCommentTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", null);

        Gson gson = new Gson();

        Response response = given()
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

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Test
    public void createOrderWithNoTokenTest() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567");

        Gson gson = new Gson();

        Response response = given()
                .header("Content-type", "application/json")
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .response();

        Assertions.assertNotNull(response.asString());
    }

    @Test
    public void getOrderById() {
        int id = orderCreationPrecondition();

        int receivedId = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");

        Assertions.assertEquals(receivedId, id);
    }

    @Test
    public void getOrderByNonExistentId() {
        int id = 2796;

        String response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .asString();

        Assertions.assertEquals("", response);
    }

    @Test
    public void getOrders() {

        int id = orderCreationPrecondition();

        OrderRealDto[] orderRealDtoArray = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);

        for (int i = 0; i < orderRealDtoArray.length; i++) {
            deleteOrderById(orderRealDtoArray[i].getId());
            System.out.println("length in loop is " + orderRealDtoArray.length);
        }

        OrderRealDto[] orderRealDtoArrayAfterDeletion = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);

        Assertions.assertEquals(0, orderRealDtoArrayAfterDeletion.length);

    }

    @Test
    public void deleteOrderByIdTest() {
        int orderId = orderCreationPrecondition();
        deleteOrderById(orderId);
    }

    @Test
    public void courierOrderAvailabilityForbiddenForStudent() {
        Response response = executeGetMethodByStudent("/orders/available");
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void courierOrderAssignForbiddenForStudent() {
        int orderId = orderCreationPrecondition();
        Response response = executePutMethodByStudent(String.format("orders/%s/assign", orderId));
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void changeOrderStatusForbiddenForStudent() {
        int orderId = orderCreationPrecondition();
        Response response = executePutMethodByStudentWithRequestBody(String.format("orders/%s/status", orderId));
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);
        System.out.println();
    }

    @Test
    public void createCourierSuccessfulTest() {
        Response response = createCourier();
        System.out.println();
    }

    public int orderCreationPrecondition() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");
        Gson gson = new Gson();

        int id = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");

        return id;

    }

    public void deleteOrderById(long id) {

        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("/orders/" + id)
                .then()
                .log()
                .all();
    }

    public Response executeGetMethodByStudent(String path) {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get(path)
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;

    }

    public Response executePutMethodByStudent(String path) {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .put(path)
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;
    }

    public Response executePutMethodByStudentWithRequestBody(String path) {

        String bodyWithStatus = "{\"status\":\"OPEN\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyWithStatus)
                .log()
                .all()
                .put(path)
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;

    }


    public Response createCourier() {

        Courier courierBody = new Courier(generateRandomLogin(), generateRandomPassword(), generateRandomName());
        Gson gson = new Gson();

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(courierBody))
                .log()
                .all()
                .post("/users/courier")
                .then()
                .log()
                .all()
                .extract()
                .response();

        return response;
    }

    public String generateRandomLogin() {
        return RandomStringUtils.random(6, true, true);
    }

    public String generateRandomPassword() {
        return RandomStringUtils.random(8, true, true);
    }

    public String generateRandomName() {
        return RandomStringUtils.random(5, true, false);
    }
}