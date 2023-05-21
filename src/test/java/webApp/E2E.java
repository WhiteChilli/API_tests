package webApp;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class E2E {

    public OrderPage login() {

        LoginPage loginPage = new LoginPage();
        loginPage.insertLogin("locmerea");
        loginPage.insertPassword("hellouser123");

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        return Selenide.page(OrderPage.class);
    }
//    OrderPage orderPage = LoginPage.login;
//
//    OrderPage order = new OrderPage();


//    $(By.xpath("//button[@data-name='createOrder-button']"));


}
