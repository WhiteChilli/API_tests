package webApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {


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
    public void incorrectLogin() {

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("Hello");
        loginPage.insertPassword("12345678");

        $(By.xpath("//button[@data-name=\"signIn-button\"]")).click();
        $(By.xpath("//div[@data-name=\"authorizationError-popup\"]"))
                .shouldBe(Condition.exist);
    }

    @Test
    public void incompleteLoginDetailsButtonDisabled() {

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("123");
        loginPage.checkSignInDisabled();
    }

    @Test
    public void successfulLogin() {

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("locmerea");
        loginPage.insertPassword("hellouser123");

        $(By.xpath("//button[@data-name=\"signIn-button\"]")).click();
        $(By.xpath("//button[@data-name=\"createOrder-button\"]"))
                .shouldBe(Condition.exist);
    }
}

