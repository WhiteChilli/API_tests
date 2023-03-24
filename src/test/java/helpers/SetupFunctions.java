package helpers;

import com.google.gson.Gson;
import dto.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {

    String baseUrl;
    String username;
    String password;

    public SetupFunctions() {
        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createUser() {
        Credentials credentials = new Credentials(username, password);
        Gson gson = new Gson();
        return gson.toJson(credentials);
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {

        return given().
                header("Content-type", "application/json").
                log().
                all().
                body( createUser() ).
                when().
                post(  baseUrl + "/login/student").
                then().
                log().
                all().
                extract().
                response().
                asString();
    }
}
