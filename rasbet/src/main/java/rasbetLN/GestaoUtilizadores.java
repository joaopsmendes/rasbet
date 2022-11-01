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
        //utilizadores.create(email,dataNAscimento,nif,password, tipo );
    }

    public void logIn(String email, String password) throws SQLException{
        utilizadores.logIn(email, password);

    }

    public void logOut(String email) throws SQLException{
        utilizadores.logOut(email);
    }

    public void replace(String email, Utilizador user) throws SQLException{
        Utilizador  oldUser = utilizadores.getUtilizador(email);
        utilizadores.replaceNome(email, selectString( oldUser.getNome(), user.getNome()));
        utilizadores.replaceEmail(email, selectString( oldUser.getEmail(), user.getEmail()));
        //replaceTelemovel
        //replaceMorada
    }
    private String selectString (String one,String two){
        if (two != null && !two.isEmpty()) return two;
        return one;
    }

    public Utilizador getByEmail(String email) throws SQLException {
        return utilizadores.getUtilizador(email);
    }



}
