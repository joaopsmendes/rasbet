package rasbetLN;

import rasbetLN.GestaoJogos.Jogo;
import rasbetLN.GestaoUtilizadores.Favorito;
import rasbetUI.ApostaRequest;
import rasbetUI.GameFutebol;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IRasbetLN {

    void validateLogin (String email, String password) throws SQLException;
    void addGame(GameFutebol gameFutebol, String bookmaker, String desporto) throws SQLException;

    // Class Apostador
    void addFavorito(String id,String desporto,String f) throws SQLException;

    void apostaSimples(ApostaRequest apostaRequest) throws SQLException;
    void apostaMultipla(ApostaRequest apostaRequest) throws SQLException;

    void deposito(String userId, float valor) throws SQLException;

    void levantamento(String userId, float valor)throws SQLException;

    void registarApostador(String email, String password,String nome, String nif, LocalDate date, String morada, String telemovel) throws SQLException;

    boolean existsGame(String gameId, String desporto) throws SQLException;

    void removeFavorito(String id, String desporto, String fav) throws SQLException;


    Map<String, Jogo> getJogos(String desporto) throws SQLException;

    void updateResultados(Map<String, String> map,String desporto) throws SQLException;

    void addNotificacao(String userId, String conteudo) throws SQLException;

    List<Favorito> getFavorites(String userId) throws SQLException;

}
