package rasbetLN.GestaoUtilizadores;

import rasbetDB.DBUtilizadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestaoUtilizadores implements IGestaoUtilizadores {
    private DBUtilizadores utilizadores;

    public GestaoUtilizadores(Connection connection){
        this.utilizadores = new DBUtilizadores(connection);
    }

    public void newApostador(Apostador apostador) throws SQLException {
        utilizadores.createApostador(apostador );
    }

    public void logIn(String email, String password) throws SQLException{
        utilizadores.logIn(email, password);
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

    @Override
    public void deposito(String userId, Deposito deposito) throws SQLException {
        updateSaldo(userId,deposito.getValor());
        utilizadores.novoDeposito(deposito, userId);
    }

    public void levantamento(String userId, Levantamento levantamento) throws SQLException{
        updateSaldo(userId, levantamento.getValor()*-1);
        float saldo = utilizadores.getSaldo(userId);
        utilizadores.novoLevantamento(levantamento, userId);
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

    public void updateSaldo(String userId, float value) throws SQLException {
        float saldo = utilizadores.getSaldo(userId);
        saldo += value;
        if (saldo < 0){
            throw new SQLException("Saldo insuficiente");
        }
        utilizadores.updateSaldo(saldo, userId);
    }

    @Override
    public double getSaldo(String userId) throws SQLException {
        return utilizadores.getSaldo(userId);
    }

}
