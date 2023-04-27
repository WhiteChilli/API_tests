package webApp;
import org.openqa.selenium.By;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;


public class EstoPage {

    public void insertUserName (String query) {
        $(By.xpath("//*[@id='login-field']/div/input")).setValue(query);
    }

    public void insertPassword (String query) {
        $(By.xpath("//*[@id='password-field']/div/input")).setValue(query);
    }
}
