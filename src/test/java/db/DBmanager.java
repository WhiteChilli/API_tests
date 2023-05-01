package db;

import helpers.SetupFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBmanager {

//    private final String url = "jdbc:postgresql://rc1b-3vawdqzm9kwkzvqn.mdb.yandexcloud.net:6432/db1";
//    private final String user = "userqa09";
//    private final String password = "GpxeB5975whsC";

    public Connection connect() {

        SetupFunctions setupFunctions = new SetupFunctions();

        String dbusername = setupFunctions.getDbusername();
        String password = setupFunctions.getDbpassword();
        String port = setupFunctions.getDbport();
        String dbname = setupFunctions.getDbname();

        String host = setupFunctions.getDbhost();
        String connectionString = host + ":" + port + "/" + dbname;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't load class for db driver");
        }


        Connection connection = null;

        try {
//          connection = DriverManager.getConnection(url, user, password);
            connection = DriverManager.getConnection(connectionString, dbusername, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Statement s = connection.createStatement();

        return connection;
    }

    public void close(Connection connection) {

        if ( connection != null ) {

            try {
                connection.close();
                System.out.println("Closed successfully");
            } catch (SQLException e) {
                System.out.println("error while closing connection:" + e);
            }

        } else {
            System.out.println("Connection does not exist");
        }

    }

}
