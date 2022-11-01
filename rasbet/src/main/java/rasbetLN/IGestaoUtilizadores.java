package rasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IGestaoUtilizadores {
    void newApostador(String email, LocalDate dataNAscimento, String nif, String password, String tipo, String nome) throws SQLException;
    void logIn(String email, String password) throws SQLException;
    void logOut(String email) throws SQLException;
    void replace(String email, Utilizador user) throws SQLException;
    Utilizador getByEmail(String email) throws SQLException;

    void addFavorito(String userId, Favorito favorito) throws SQLException;

    void removeFavorito(String id, Favorito fav);
}
