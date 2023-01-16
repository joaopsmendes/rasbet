package rasbetLN.GestaoUtilizadores;

import rasbetDB.DBUtilizadores;
import rasbetLN.GestaoJogos.Desporto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestaoUtilizadores implements IGestaoUtilizadores {
    private DBUtilizadores utilizadores;

    public GestaoUtilizadores(Connection connection){
        this.utilizadores = new DBUtilizadores(connection);
    }

    public String getUtilizadorSessao(String idSessao) throws SQLException{
        return utilizadores.getUtilizadorSessao(idSessao);
    }

    public String newApostador(Apostador apostador) throws SQLException {
        Sessao s = new Sessao(apostador.getEmail());
        utilizadores.createApostador(apostador);
        this.utilizadores.createSessao(s);
        return s.getIdSession();
    }

    public Map<String, String> logIn(String email, String password) throws SQLException {
        Sessao s = new Sessao(email);
        this.utilizadores.updateSessao(s);
        Map<String,String> res = new HashMap<>();
        res.put("sessionId",s.getIdSession());
        res.put("tipo",utilizadores.logIn(email, password));
        return res;
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


    public List<String> getUtilizadoresFav(String favorito, Desporto desporto) throws SQLException {
        return utilizadores.getUtilizadoresFav(favorito, desporto);
    }

    public void addJogoASeguir(String idJogo, int idDesporto, String idUser) throws SQLException{
        this.utilizadores.addJogoASeguir(idJogo,idDesporto,idUser);
    }

    public void removeJogoASeguir(String idJogo, int idDesporto, String idUser) throws SQLException{
        this.utilizadores.removeJogoASeguir(idJogo, idDesporto, idUser);
    }

    public Map<Integer, List<String>> getJogosASeguir(String idUser) throws SQLException{
        return utilizadores.getJogosASeguir(idUser);
    }

    public String getUserid(String idSessao) throws SQLException{
        return utilizadores.getUtilizadorSessao(idSessao);
    }

    public List<Promocao> getPromoApostaSegura(String userId) throws SQLException{
        return utilizadores.getPromoApostaSegura(userId);
    }

    public List<Promocao> getPromoFreeBetsDeposito(String userId) throws SQLException{
        return utilizadores.getPromoFreeBetsDeposito(userId);
    }

    public List<String> getUsersPromocao(int promoId) throws SQLException{
        return utilizadores.getUsersPromocao(promoId);
    }

    public int createPromocaoApostaSegura (int limite) throws SQLException{
        int idPromocao = utilizadores.createPromocao();
        utilizadores.createPromocaoApostaSegura(idPromocao, limite);
        return idPromocao;
    }

    public int createPromocaoFreebetsAposDeposito(int deposito, int freebets) throws SQLException{
        int idPromocao = utilizadores.createPromocao();
        utilizadores.createPromocaoFreebetsAposDeposito(idPromocao,deposito, freebets);
        return idPromocao;
    }

    public List<String> getIdApostadores() throws SQLException{
        return utilizadores.getIdApostadores();
    }

    @Override
    public int getValorPromocaoDeposito(int promocao) throws SQLException {
        return utilizadores.getValorPromocaoDeposito(promocao);
    }

    @Override
    public List<String> getUtilizadoresJogoASeguir(String idJogo) throws SQLException {
        return utilizadores.getUtilizadoresJogoASeguir(idJogo);
    }

    @Override
    public void addPromocao(String user, int idPromocao) throws SQLException {
        utilizadores.addPromocao(user, idPromocao);
    }


}
