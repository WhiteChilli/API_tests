package webApp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class OrderPage {

    public void insertName (String query) {
        $(By.xpath("//input[@data-name='username-input']")).setValue(query);
    }

    public void insertPhone (String query) {
        $(By.xpath("//input[@data-name='phone-input']")).setValue(query);
    }

    public void insertComment (String query) {
        $(By.xpath("//input[@data-name='comment-input']")).setValue(query);
    }



}

