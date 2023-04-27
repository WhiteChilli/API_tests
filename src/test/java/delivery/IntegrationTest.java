package delivery;
import db.DBmanager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class IntegrationTest {

    @Test
    public void dummy() {

        DBmanager dbmanager = new DBmanager();

        Connection connection = dbmanager.connect();

        dbmanager.close(connection);
    }
}
