package rasbetLN.GestaoUtilizadores;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestaoUtilizadores {
    void newApostador(Apostador apostador) throws SQLException;
    void logIn(String email, String password) throws SQLException;
    void logOut(String email) throws SQLException;
    void replace(String email, Utilizador user) throws SQLException;
    Utilizador getByEmail(String email) throws SQLException;

    void addFavorito(String userId, Favorito favorito) throws SQLException;

    void removeFavorito(String id, Favorito fav) throws SQLException;

    List<Transacao> getHistTransacoes(String userId) throws SQLException;

    void addNotificacao(String idUser, Notificacao notificacao) throws SQLException;

    List<Favorito> getFavoritos(String idUser) throws SQLException;

    void updateSaldoFreebets(String userId, float saldo, float freebets) throws SQLException;

    Map<String, Float> getSaldoFreeBets(String userId) throws SQLException;

    Map<String, String> info(String userId)throws SQLException;

    void updateSaldo(String userId, float value)throws SQLException;

    void transacao(String userId, Transacao transacao)throws SQLException;;
}
