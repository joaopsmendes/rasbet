package rasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IRasbetUtilizador {
    void newUser(String email, LocalDate dataNAscimento, String nif, String password, String tipo) throws SQLException;
    void logIn(String email, String password) throws SQLException;
    void logOut(String email) throws SQLException;
    void replace(String email, Utilizador user) throws SQLException;
}
