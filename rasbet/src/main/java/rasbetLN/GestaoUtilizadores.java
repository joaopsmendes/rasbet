package rasbetLN;

import rasbetDB.DBUtilizadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestaoUtilizadores implements IRasbetUtilizador{
    private DBUtilizadores utilizadores;

    public GestaoUtilizadores(Connection connection){
        this.utilizadores = new DBUtilizadores(connection);
    }

    public void newUser(String email, LocalDate dataNAscimento, String nif, String password, String tipo) throws SQLException {
        utilizadores.create(email,dataNAscimento,nif,password, tipo );
    }

    public void logIn(String email, String password) throws SQLException{
        utilizadores.logIn(email, password);

    }

    public void logOut(String email) throws SQLException{
        utilizadores.logOut(email);
    }

    void replace(String email, Utilizador user) throws SQLException{
        //replaceNome
        //replaceEmail
        //replaceTelemovel
        //replaceMorada
    }



}
