package delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import helpers.SetupFunctions;
import db.DBmanager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static com.codeborne.selenide.Selenide.$;

public class IntegrationTest {

    static Connection connection;
    static DBmanager dBmanager;
    public static String token;

    @BeforeAll
    public static void setUp() {

        // open connection
        dBmanager = new DBmanager();
        connection = dBmanager.connect();

        Assumptions.assumeTrue(connection != null,
                "No connection to db. Infrastructure failure");

        SetupFunctions setupFunctions = new SetupFunctions();
        System.out.println("token: " + setupFunctions.getToken());
        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();

    }

    @AfterAll
    public static void tearDown() {
        // close connection
        dBmanager.close(connection);
    }


    @Test
    public void dummy() {

        SelenideElement usernameInput = $(By.id("username")).setValue("vladat");
        SelenideElement passwordInput = $(By.id("password")).setValue("password");
        $(By.xpath("//*[@data-name = 'signIn-button']")).click();

        $(By.id("name")).setValue("order-name");
        $(By.id("phone")).setValue("phone-name");
        $(By.xpath("//*[@data-name = 'createOrder-button']")).click();

        // explain
        //  sleep(500);
        String successText = $(By.xpath(
                "//*[@data-name = 'orderSuccessfullyCreated-popup-close-button']/following-sibling::span"))
                .shouldBe(Condition.visible)
                .getAttribute("innerHTML");

        String s = successText.replaceAll("[^0-9]", "");

        int orderId = Integer.parseInt( s );

        executeSearchAndCompare( orderId );
    }



    public void executeSearchAndCompare(int orderId) {

        String sqlToFail = String.format("select * from orders where id >= %d ;", orderId - 2);

        // step 1
        String sql = String.format("select * from orders where id = %d ;", orderId);

        System.out.println();

        try {
            System.out.println("Executing sql ... ");
            System.out.println("SQL is : " + sql);

            // step 2
            Statement statement = connection.createStatement();
            // step 3
            ResultSet resultSet = statement.executeQuery(sql);

            //int size = 0;
            String statusFromDb = null;
            if (resultSet != null) {

                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                    statusFromDb = resultSet.getString(3);
                    //size++;
                }

                // only 1 record should be with status OPEN
                // Assertions.assertEquals(1, size);


                Assertions.assertEquals(Status.OPEN.toString(), statusFromDb);


            } else {
                Assertions.fail("Result set is null");
            }

        } catch (SQLException e) {

            System.out.println("Error while executing sql ");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            e.printStackTrace();

            Assertions.fail("SQLException");

        }
    }
}