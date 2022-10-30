package rasbetLN;

import rasbetUI.Game;

import java.time.LocalDate;

public interface IRasbetLN {
    void addGame(Game game, String bookmaker, String desporto);
    void newUser(String email, LocalDate dataNAscimento, String nif, String password) throws SQLException;
    void logIn(String email, String password) throws SQLException;
    void logOut(String email) throws SQLException;


    // Class Apostador
    void addFavorito(String e);
}
