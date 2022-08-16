package by.lev.repository.user;

public interface UserReposiroryQuery {

    String USER_REPOSITORY_CREATE_SCRIPT = "insert into bsbank.users (first_name, last_name, birth, phone_number, email, secret_word, secret_word_hint, registration_date, modification_date, is_deleted) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    String USER_REPOSITORY_UPDATE_SCRIPT = "update bsbank.users set id = ?, first_name = ?, last_name = ?, birth = ?, phone_number = ?, email = ?, secret_word = ?, secret_word_hint = ?, registration_date = ?, modification_date = ?, is_deleted = ? where id = ?;";

    String USER_REPOSITORY_DELETE_SCRIPT = "delete from bsbank.users where id = ?;";
}