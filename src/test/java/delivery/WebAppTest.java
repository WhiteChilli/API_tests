package delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class WebAppTest {


    @Test
    public void incorrectLogin() {

        Configuration.browser = "firefox";
        open("http://51.250.6.164:3000/signin");
        SelenideElement usernameInput = $(By.xpath("//input[@data-name=\"username-input\"]"));
        SelenideElement passwordInput = $(By.xpath("//input[@data-name=\"password-input\"]"));


        usernameInput.setValue("Hello");
        passwordInput.setValue("12345678");

        $(By.xpath("//button[@data-name=\"signIn-button\"]")).click();
        $(By.xpath("//div[@data-name=\"authorizationError-popup\"]"))
                .shouldBe(Condition.exist);
    }

    @Test
    public void successfulLogin() {

        Configuration.browser = "firefox";
        open("http://51.250.6.164:3000/signin");
        SelenideElement usernameInput = $(By.xpath("//input[@data-name=\"username-input\"]"));
        SelenideElement passwordInput = $(By.xpath("//input[@data-name=\"password-input\"]"));

        usernameInput.setValue("locmerea");
        passwordInput.setValue("hellouser123");

        $(By.xpath("//button[@data-name=\"signIn-button\"]")).click();
        $(By.xpath("//button[@data-name=\"createOrder-button\"]"))
                .shouldBe(Condition.exist);
    }
}

