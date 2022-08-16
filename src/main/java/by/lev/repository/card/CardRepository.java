package by.lev.repository.card;

import by.lev.domain.Card;
import by.lev.exception.NoSuchEntityException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static by.lev.repository.card.CardTableColumns.ID;
import static by.lev.repository.card.CardTableColumns.CARD_NUMBER;
import static by.lev.repository.card.CardTableColumns.CREATED;
import static by.lev.repository.card.CardTableColumns.CHANGED;
import static by.lev.repository.card.CardTableColumns.VALID_PERIOD;
import static by.lev.repository.card.CardTableColumns.IS_ACTIVE;
import static by.lev.repository.card.CardTableColumns.IS_BLOCKED;

import static by.lev.repository.repositoryConnection.RepositoryConnection.getConnection;

public class CardRepository implements CardRepositoryInterface{
    @Override
    public Card findById(Long id) {
        final String findByIdQuery = "select * from bsbank.cards where id = " + id;

        Connection connection;
        Statement statement;
        ResultSet rs;

        try{
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findByIdQuery);

            boolean hasRow = rs.next();
            if(hasRow){
                return cardRowMapping(rs);
            } else{
                throw new NoSuchEntityException("Entity Card with id " + id + " does not exist", 404);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public List<Card> findAll(int limit, int offset) {
        return null;
    }

    @Override
    public Card create(Card card) {
        return null;
    }

    @Override
    public Card update(Card card) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    private Card cardRowMapping(ResultSet rs) throws SQLException {
        Card card = new Card();
        card.setId(rs.getLong(ID));
        card.setCardNumber(rs.getLong(CARD_NUMBER));
        card.setRegistrationDate(rs.getTimestamp(CREATED));
        card.setModificationDate(rs.getTimestamp(CHANGED));
        card.setValidPeriod(rs.getDate(VALID_PERIOD));
        card.setIsActive(rs.getBoolean(IS_ACTIVE));
        card.setIsBlocked(rs.getBoolean(IS_BLOCKED));
        return card;
    }
}
