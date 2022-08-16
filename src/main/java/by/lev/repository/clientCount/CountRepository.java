package by.lev.repository.clientCount;

import by.lev.domain.ClientCount;
import by.lev.exception.NoSuchEntityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lev.repository.clientCount.CountTableColumns.ID;
import static by.lev.repository.clientCount.CountTableColumns.COUNT;
import static by.lev.repository.clientCount.CountTableColumns.CREATED;
import static by.lev.repository.clientCount.CountTableColumns.CHANGED;

import static by.lev.repository.repositoryConnection.RepositoryConnection.getConnection;
import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_CREATE_SCRIPT;
import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_DELETE_SCRIPT;
import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_UPDATE_SCRIPT;


public class CountRepository implements CountRepositoryInterface{
    @Override
    public ClientCount findById(Long id) {
        final String findByIdQuery = "select * from bsbank.client_count where id = " + id;

        Connection connection;
        Statement statement;
        ResultSet rs;

        try{
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findByIdQuery);

            boolean hasRow = rs.next();
            if (hasRow) {
                return countRowMapping(rs);
            } else {
                throw new NoSuchEntityException("Entity Client Count with id " + id + " does not exist", 404);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ClientCount> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<ClientCount> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<ClientCount> findAll(int limit, int offset) {
        final String findAllQuery =
                "select * from bsbank.client_count order by id limit " + limit + " offset " + offset;

        List<ClientCount> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try{
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            while (rs.next()){
                result.add(countRowMapping(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ClientCount create(ClientCount clientCount) {
        final String updateQuery = "insert into bsbank.client_count () values ();";
        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(updateQuery);

            statement.executeUpdate();

            ResultSet resultSet = connection.prepareStatement("SELECT currval('bsbank.client_count_id_seq') as last_id").executeQuery();
            resultSet.next();
            long countLastInsertId = resultSet.getLong("last_id");

            return findById(countLastInsertId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public ClientCount update(ClientCount clientCount) {
        final String updateQuery = "update bsbank.client_count set id = ?, count = ?, registration_date = ?, modification_date = ? where id = ?;";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(updateQuery);

            statement.setLong(1, clientCount.getId());
            statement.setDouble(2, clientCount.getCount());
            statement.setTimestamp(3, clientCount.getRegistrationDate());
            statement.setTimestamp(4, clientCount.getModificationDate());
            statement.setLong(5, clientCount.getId());

            statement.executeUpdate();

            return findById(clientCount.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(Long id) {
        final String deleteQuery = "delete from bsbank.client_count where id = ?;";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(deleteQuery);

            statement.setLong(1, id);
            statement.executeUpdate();

            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private ClientCount countRowMapping(ResultSet rs) throws SQLException {
        ClientCount count = new ClientCount();
        count.setId(rs.getLong(ID));
        count.setCount(rs.getDouble(COUNT));
        count.setRegistrationDate(rs.getTimestamp(CREATED));
        count.setModificationDate(rs.getTimestamp(CHANGED));

        return count;
    }
}
