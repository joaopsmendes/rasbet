package rasbetLN;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.Jogo;
import rasbetLN.GestaoUtilizadores.Favorito;
import rasbetLN.GestaoUtilizadores.Notificacao;
import rasbetLN.GestaoUtilizadores.Promocao;
import rasbetLN.GestaoUtilizadores.Transacao;
import rasbetUI.ApostaRequest;
import rasbetUI.Game;
import rasbetUI.GameOutput;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRasbetLN {

    Map<String, String> validateLogin (String email, String password) throws SQLException;
    void addGame(Game game, String bookmaker, String desporto) throws SQLException;

    void addGame(GameOutput game) throws SQLException;

    // Class Apostador
    void addFavorito(String id,String desporto,String f) throws SQLException;

    void deposito(String userId, float valor, int promocao) throws SQLException;

    void deposito(String userId, float valor) throws SQLException;

    void levantamento(String userId, float valor)throws SQLException;

    String registarApostador(String email, String password,String nome, String nif, LocalDate date, String morada, String telemovel) throws SQLException;

    boolean existsGame(String gameId, String desporto) throws SQLException;

    void removeFavorito(String id, String desporto, String fav) throws SQLException;

    Map<String, Jogo> getJogos(String desporto) throws SQLException;

    void updateResultados(Map<String, String> map,String desporto) throws SQLException;

    void addNotificacao(String userId, String conteudo) throws SQLException;

    List<Notificacao> getNotifications(String userId) throws SQLException;

    List<Favorito> getFavorites(String userId) throws SQLException;

    public void fecharAposta(String userId,int idAposta, boolean resultado) throws SQLException;

    void aposta(ApostaRequest apostaRequest) throws SQLException;

    List<Aposta> historicoApostas(String userId) throws SQLException;

    List<Transacao> historicoTransacoes(String userId) throws SQLException;

    void alterarOdd(int idOdd, float valor) throws SQLException;

    void cashout(int idAposta,String userId) throws SQLException;

    void alterarPerfil(String userID, Map<String, String> dados) throws SQLException;

    List<Desporto> getDesporto();

    Map<String, Float>  getSaldo(String userId) throws SQLException;

    Map<String, String> infoUser(String userId)throws SQLException;

    void suspenderJogo(String idJogo) throws SQLException;

    void ativarJogo(String idJogo) throws SQLException;


    void updateEstadoJogos() throws SQLException;

    void sendNotificacao(Notificacao notificacao) throws SQLException;

    void sendNotificacao(String conteudo) throws SQLException;

    void removeNotificacao(String userId, int notificacao) throws SQLException;

    void vistaNotificacao(String userId, int notificacao) throws SQLException;

    void sendNotificacaoUtilizador(String userId, String conteudo) throws SQLException;

    void addJogoASeguir(String idJogo, String desporto, String idUser) throws SQLException;

    void removeJogoASeguir(String idJogo, String desporto, String idUser) throws SQLException;

//    void alterarOdd(int idOdd, float valor, String conteudo) throws SQLException;

    Map<String, List<String>> getJogosASeguir(String idUser) throws SQLException;

    List<Promocao> getPromoApostaSegura(String userId) throws SQLException;

    List<Promocao> getPromoFreeBetsDeposito(String userId) throws SQLException;

    List<String> getUsersPromocao(int promoId) throws SQLException;

    //void sendNotPromo(int idPromo) throws SQLException;

    void createPromocaoFreeBetsDeposito(int deposito, int freeBets) throws SQLException;

    void createPromocaoApostaSegura(int limite) throws SQLException;

}
