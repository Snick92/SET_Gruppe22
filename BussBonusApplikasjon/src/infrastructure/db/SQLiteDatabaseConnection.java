package infrastructure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabaseConnection {

    private final String url;

    public SQLiteDatabaseConnection(String url) {
        this.url = url;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
