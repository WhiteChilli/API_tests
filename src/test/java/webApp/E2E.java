package webApp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class E2E {

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
        loginPage.insertLogin("locmerea");
        loginPage.insertPassword("hellouser123");

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


//    OrderPage orderPage = LoginPage.login;

//    $(By.xpath("//button[@data-name='createOrder-button']"));


}
