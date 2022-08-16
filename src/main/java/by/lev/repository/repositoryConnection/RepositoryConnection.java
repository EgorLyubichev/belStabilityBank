package by.lev.repository.repositoryConnection;

import by.lev.util.DatabasePropertiesReader;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static by.lev.util.DatabasePropertiesReader.DATABASE_LOGIN;
import static by.lev.util.DatabasePropertiesReader.DATABASE_NAME;
import static by.lev.util.DatabasePropertiesReader.DATABASE_PASSWORD;
import static by.lev.util.DatabasePropertiesReader.DATABASE_PORT;
import static by.lev.util.DatabasePropertiesReader.DATABASE_URL;
import static by.lev.util.DatabasePropertiesReader.POSTRGES_DRIVER_NAME;

public class RepositoryConnection {
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName(DatabasePropertiesReader.getProperty(POSTRGES_DRIVER_NAME));
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String url = DatabasePropertiesReader.getProperty(DATABASE_URL);
        String port = DatabasePropertiesReader.getProperty(DATABASE_PORT);
        String dbName = DatabasePropertiesReader.getProperty(DATABASE_NAME);
        String login = DatabasePropertiesReader.getProperty(DATABASE_LOGIN);
        String password = DatabasePropertiesReader.getProperty(DATABASE_PASSWORD);

        String jdbcURL = StringUtils.join(url, port, dbName);

        return DriverManager.getConnection(jdbcURL, login, password);
    }
}
