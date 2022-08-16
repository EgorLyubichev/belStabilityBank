package by.lev.repository.user;

import by.lev.domain.User;
import by.lev.exception.NoSuchEntityException;
import by.lev.repository.repositoryConnection.RepositoryConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.lev.repository.repositoryConnection.RepositoryConnection.getConnection;

import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_CREATE_SCRIPT;
import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_DELETE_SCRIPT;
import static by.lev.repository.user.UserReposiroryQuery.USER_REPOSITORY_UPDATE_SCRIPT;

import static by.lev.repository.user.UserTableColumns.ID;
import static by.lev.repository.user.UserTableColumns.NAME;
import static by.lev.repository.user.UserTableColumns.LASTNAME;
import static by.lev.repository.user.UserTableColumns.BIRTH_DATE;
import static by.lev.repository.user.UserTableColumns.PHONE_NUMBER;
import static by.lev.repository.user.UserTableColumns.EMAIL;
import static by.lev.repository.user.UserTableColumns.SECRET_WORD;
import static by.lev.repository.user.UserTableColumns.SECRET_WORD_HINT;
import static by.lev.repository.user.UserTableColumns.CREATED;
import static by.lev.repository.user.UserTableColumns.CHANGED;
import static by.lev.repository.user.UserTableColumns.IS_DELETED;

public class UserRepository implements UserRepositoryInterface {
    @Override
    public User findById(Long id) {
        final String findByIdQuery = "select * from bsbank.users where id = " + id;

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {

            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findByIdQuery);
            boolean hasRow = rs.next();
            if (hasRow) {
                return userRowMapping(rs);
            } else {
                throw new NoSuchEntityException("Entity User with id " + id + " does not exist", 404);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        final String findAllQuery =
                "select * from bsbank.users order by id limit " + limit + " offset " + offset;

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findAllQuery);

            while (rs.next()) {
                result.add(userRowMapping(rs));
            }

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User create(User user) {

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(USER_REPOSITORY_CREATE_SCRIPT);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, user.getBirth());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getSecretWord());
            statement.setString(7, user.getSecretWordHint());
            statement.setTimestamp(8, user.getRegistrationDate());
            statement.setTimestamp(9, user.getModificationDate());
            statement.setBoolean(10, user.getIsDeleted());

            statement.executeUpdate();

            //executeUpdate - for INSERT, UPDATE, DELETE
            //executeQuery(); - for SELECT

            /*Get users last insert id for finding new object in DB*/
            ResultSet resultSet = connection.prepareStatement("SELECT currval('bsbank.users_id_seq') as last_id").executeQuery();
            resultSet.next();
            long userLastInsertId = resultSet.getLong("last_id");

            return findById(userLastInsertId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User update(User user) {

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(USER_REPOSITORY_UPDATE_SCRIPT);

            statement.setLong(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, user.getBirth());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getSecretWord());
            statement.setString(8, user.getSecretWordHint());
            statement.setTimestamp(9, user.getRegistrationDate());
            statement.setTimestamp(10, user.getModificationDate());
            statement.setBoolean(11, user.getIsDeleted());
            statement.setLong(12, user.getId());

            statement.executeUpdate();

            return findById(user.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(Long id) {

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(USER_REPOSITORY_DELETE_SCRIPT);

            statement.setLong(1, id);
            statement.executeUpdate();

            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private User userRowMapping(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setFirstName(rs.getString(NAME));
        user.setLastName(rs.getString(LASTNAME));
        user.setBirth(rs.getDate(BIRTH_DATE));
        user.setPhoneNumber(rs.getString(PHONE_NUMBER));
        user.setEmail(rs.getString(EMAIL));
        user.setSecretWord(rs.getString(SECRET_WORD));
        user.setSecretWordHint(rs.getString(SECRET_WORD_HINT));
        user.setRegistrationDate(rs.getTimestamp(CREATED));
        user.setModificationDate(rs.getTimestamp(CHANGED));
        user.setIsDeleted(rs.getBoolean(IS_DELETED));
        return user;
    }
}
