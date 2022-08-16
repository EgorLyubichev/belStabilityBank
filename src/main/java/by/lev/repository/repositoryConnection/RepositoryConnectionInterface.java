package by.lev.repository.repositoryConnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface RepositoryConnectionInterface {
    Connection getConnection() throws SQLException;
}
