package rasbetLN.GestaoUtilizadores;

import rasbetLN.GestaoJogos.Desporto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestaoUtilizadores {
    String newApostador(Apostador apostador) throws SQLException;

    Map<String, String> logIn(String email, String password) throws SQLException;

    void logOut(String email) throws SQLException;

    void replace(String email, Utilizador user) throws SQLException;

    Utilizador getByEmail(String email) throws SQLException;

    void addFavorito(String userId, Favorito favorito) throws SQLException;

    void removeFavorito(String id, Favorito fav) throws SQLException;

    List<Transacao> getHistTransacoes(String userId) throws SQLException;

    void addNotificacao(String idUser, Notificacao notificacao) throws SQLException;

    List<Notificacao> getNotificacao(String idUser) throws SQLException;

    List<Favorito> getFavoritos(String idUser) throws SQLException;

    void updateSaldoFreebets(String userId, float saldo, float freebets) throws SQLException;

    Map<String, Float> getSaldoFreeBets(String userId) throws SQLException;

    Map<String, String> info(String userId) throws SQLException;

    void updateSaldo(String userId, float value) throws SQLException;

    void transacao(String userId, Transacao transacao) throws SQLException;

    int updateStreak(String id, float valor) throws SQLException;

    float bonusStreak(String key) throws SQLException;

    void sendNotificacao(Notificacao notificacao) throws SQLException;

    void sendNotificacao(String conteudo) throws SQLException;

    void removeNotificacao(String idUser, int notificacao) throws SQLException;

    void vistaNotificacao(String idUser, int notificacao) throws SQLException;

    void sendNotificacaoUtilizador(String userId, String conteudo) throws SQLException;

    List<String> getUtilizadoresFav(String favorito, Desporto desporto) throws SQLException;

    void addJogoASeguir(String idJogo, int idDesporto, String idUser) throws SQLException;

    void removeJogoASeguir(String idJogo, int idDesporto, String idUser) throws SQLException;

    Map<Integer, List<String>> getJogosASeguir(String idUser) throws SQLException;

    String getUserid(String idSessao) throws SQLException;

    List<Promocao> getPromoApostaSegura(String userId) throws SQLException;

    List<Promocao> getPromoFreeBetsDeposito(String userId) throws SQLException;

    List<String> getUsersPromocao(int promoId) throws SQLException;

    int createPromocaoApostaSegura (int limite) throws SQLException;

    int createPromocaoFreebetsAposDeposito(int deposito, int freebets) throws SQLException;

    List<String> getIdApostadores() throws SQLException;

    int getValorPromocaoDeposito(int promocao) throws SQLException;

    List<String> getUtilizadoresJogoASeguir(java.lang.String idJogo) throws SQLException;

    void addPromocao(String user, int idPromocao) throws SQLException;

    void promocaoUtilizada (String user, int idPromocao) throws SQLException;

    }
