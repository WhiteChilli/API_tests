package helpers;

import com.google.gson.Gson;
import dto.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {

    //api
    String baseUrl;
    String baseUrlFrontEnd;
    String username;
    String password;

    //db
    String dbhost;
    String dbusername;
    String dbpassword;
    String dbport;
    String dbname;


    public SetupFunctions() {
        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            //api
            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            baseUrlFrontEnd = properties.getProperty("baseUrlFrontEnd");

            //db
            dbhost = properties.getProperty("dbhost");
            dbusername = properties.getProperty("dbusername");
            dbpassword = properties.getProperty("dbpassword");
            dbport = properties.getProperty("dbport");
            dbname = properties.getProperty("dbname");

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

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }

    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
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

    public String getBaseUrlFrontEnd() {
        return baseUrlFrontEnd;
    }
}
