package rasbetLN.GestaoUtilizadores;

import rasbetDB.DBUtilizadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public List<Favorito> getFavoritos(String idUser) throws SQLException{
        return utilizadores.getFavoritos(idUser);
    }

    public void updateSaldoFreebets(String userId, float saldo, float freebets) throws SQLException {
        Map<String, Float> mapSaldo = utilizadores.getSaldoFreeBets(userId);
        float saldoAtual = mapSaldo.get("saldo");
        saldoAtual -= saldo;
        if (saldoAtual < 0){
            throw new SQLException("Saldo Insuficiente");
        }
        float freebestAtual = mapSaldo.get("freebets");
        freebestAtual-=freebets;
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


}
