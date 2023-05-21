package webApp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import helpers.SetupFunctions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class E2E {

    SetupFunctions setupFunctions = new SetupFunctions();
    String name = setupFunctions.getUsername();
    String pass = setupFunctions.getPassword();

    @BeforeEach
    public void setUp() {
        Configuration.browser = "firefox";
        open("http://51.250.6.164:3000/signin");

    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public OrderPage login() {


        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin(name);
        loginPage.insertPassword(pass);

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        return Selenide.page(OrderPage.class);
    }


    @Test
        public void test() {

        OrderPage orderPage = new OrderPage();
        orderPage.insertName("Test");
        orderPage.insertPhone("123456");
        orderPage.insertComment("Big order");

        $(By.xpath("//button[@data-name='createOrder-button']")).click();
    }


}
