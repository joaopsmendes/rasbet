package rasbetLN.GestaoUtilizadores;

import java.sql.SQLException;
import java.util.List;

public interface IGestaoUtilizadores {
    void newApostador(Apostador apostador) throws SQLException;
    void logIn(String email, String password) throws SQLException;
    void logOut(String email) throws SQLException;
    void replace(String email, Utilizador user) throws SQLException;
    Utilizador getByEmail(String email) throws SQLException;

    void addFavorito(String userId, Favorito favorito) throws SQLException;

    void removeFavorito(String id, Favorito fav) throws SQLException;

    void deposito(String userId, Deposito deposito) throws SQLException;

    void levantamento(String userId, Levantamento levantamento) throws SQLException;

    //void replaceTelemovel(String email, String telemovel) throws SQLException;

    //void replaceMorada(String email, String morada) throws SQLException;

    void updateFreebets(float freebets, String userId) throws SQLException;

    List<Transacao> getHistTransacoes(String userId) throws SQLException;

    void fazerAposta(String userId, float montante) throws SQLException;
}
