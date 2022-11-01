package rasbetLN;

import rasbetDB.DBUtilizadores;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class GestaoUtilizadores implements IGestaoUtilizadores {
    private DBUtilizadores utilizadores;

    public GestaoUtilizadores(Connection connection){
        this.utilizadores = new DBUtilizadores(connection);
    }

    public void newApostador(String email, LocalDate dataNAscimento, String nif, String password, String tipo, String nome) throws SQLException {
        utilizadores.createApostador(email,dataNAscimento,nif,password, tipo, nome );
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

    @Override
    public void addFavorito(String userId, Favorito favorito) {

    }

    @Override
    public void removeFavorito(String id, Favorito fav) {

    }


}
