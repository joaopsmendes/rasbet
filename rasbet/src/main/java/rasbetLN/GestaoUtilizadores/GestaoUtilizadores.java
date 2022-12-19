package rasbetLN.GestaoUtilizadores;

import rasbetDB.DBUtilizadores;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.Jogo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestaoUtilizadores implements IGestaoUtilizadores {
    private DBUtilizadores utilizadores;

    public GestaoUtilizadores(Connection connection){
        this.utilizadores = new DBUtilizadores(connection);
    }

    public void newApostador(Apostador apostador) throws SQLException {
        utilizadores.createApostador(apostador );
    }

    public String logIn(String email, String password) throws SQLException{
        return utilizadores.logIn(email, password);
    }

    public void logOut(String email) throws SQLException{
        utilizadores.logOut(email);
    }

    public void replace(String email, Utilizador user) throws SQLException{
//        Utilizador  oldUser = utilizadores.getUtilizador(email);
        utilizadores.replaceUtilizador(user, email);
//        utilizadores.replaceNome(email, selectString( oldUser.getNome(), user.getNome()));
//        utilizadores.replaceEmail(email, selectString( oldUser.getEmail(), user.getEmail()));
//        utilizadores.replaceTelemovel(email, selectString(oldUser.getTelemovel(), user.getTelemovel()));
//        utilizadores.replaceMorada(email, selectString(oldUser.getMorada(), user.getMorada()));

    }
    private String selectString (String one,String two){
        if (two != null && !two.isEmpty()) return two;
        return one;
    }

    public Utilizador getByEmail(String email) throws SQLException {
        return utilizadores.getUtilizador(email);
    }

    @Override
    public void addFavorito(String userId, Favorito favorito) throws SQLException {
        utilizadores.addFavorito(userId, favorito);
    }

    @Override
    public void removeFavorito(String id, Favorito fav) throws SQLException {
        utilizadores.removeFavorito(id, fav);
    }

    public void updateFreebets(float freebets, String userId) throws SQLException{
        this.utilizadores.updateFreebets(freebets,userId);
    }


    public List<Transacao> getHistTransacoes(String userId) throws SQLException{
        return this.utilizadores.getHistTransacoes(userId);
    }

    public void addNotificacao(String idUser, Notificacao notificacao) throws SQLException{
        utilizadores.addNotificacao(idUser, notificacao);
    }

    public List<Notificacao> getNotificacao(String idUser) throws SQLException{
        return utilizadores.getListaNotificacao(idUser);
    }

    public List<Favorito> getFavoritos(String idUser) throws SQLException{
        return utilizadores.getFavoritos(idUser);
    }

    public void updateSaldoFreebets(String userId, float saldo, float freebets) throws SQLException {
        Map<String, Float> mapSaldo = utilizadores.getSaldoFreeBets(userId);
        float saldoAtual = mapSaldo.get("saldo");
        saldoAtual += saldo;
        if (saldoAtual < 0){
            throw new SQLException("Saldo Insuficiente");
        }
        float freebestAtual = mapSaldo.get("freebets");
        freebestAtual+=freebets;
        if (freebestAtual < 0){
            throw new SQLException("Freebets Insuficiente");
        }
        utilizadores.updateSaldoFreebets(saldoAtual,freebestAtual,userId);
    }

    public void updateSaldo(String userId, float saldo) throws SQLException {
        float saldoAtual = utilizadores.getSaldo(userId);
        saldoAtual += saldo;
        if (saldoAtual < 0){
            throw new SQLException("Saldo Insuficiente");
        }

        utilizadores.updateSaldo(saldoAtual,userId);
    }

    @Override
    public void transacao(String userId, Transacao transacao) throws SQLException {
        utilizadores.novaTransacao(transacao,userId);
    }

    @Override
    public int updateStreak(String id, float valor) throws SQLException {
        return utilizadores.updateStreak(id, valor);
    }

    @Override
    public float bonusStreak(String key) throws SQLException {
        return utilizadores.bonusStreak(key);
    }

    public float getSaldo(String userId) throws SQLException {
        return utilizadores.getSaldo(userId);
    }

    @Override
    public Map<String, Float> getSaldoFreeBets(String userId) throws SQLException {
        return utilizadores.getSaldoFreeBets(userId);
    }

    @Override
    public Map<String, String> info(String userId) throws SQLException{
        return utilizadores.info(userId);
    }


    public void sendNotificacao(Notificacao notificacao) throws SQLException {
        List<String> lista = utilizadores.getIdApostadores();
        for (String email : lista){
            utilizadores.addNotificacao(email, notificacao);
        }
    }

    public void sendNotificacao(String conteudo) throws SQLException {
        Notificacao notificacao = new Notificacao(conteudo,false);
        List<String> lista = utilizadores.getIdApostadores();
        for (String email : lista){
            utilizadores.addNotificacao(email, notificacao);
        }
    }

    public void sendNotificacaoUtilizador(String userId, String conteudo) throws SQLException{
        Notificacao not = new Notificacao(conteudo, false);
        utilizadores.addNotificacao(userId, not);
    }

    @Override
    public void removeNotificacao(String idUser, int notificacao) throws SQLException {
        utilizadores.removeNotificacao(idUser, notificacao);
    }

    public void vistaNotificacao(String idUser, int notificacao) throws SQLException {
        utilizadores.vistaNotificacao(idUser, notificacao);
    }

    public Set<String> getUtilizadoresOddS(int idOdd) throws SQLException {
        return utilizadores.getUtilizadoresOddS(idOdd);
    }

    public Set<String> getUtilizadoresOddM(int idOdd) throws SQLException {
        return utilizadores.getUtilizadoresOddM(idOdd);
    }

    public List<String> getUtilizadoresFav(String favorito, Desporto desporto) throws SQLException {
        return utilizadores.getUtilizadoresFav(favorito, desporto);
    }

}
