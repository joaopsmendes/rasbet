package rasbetLN;

import rasbetUI.ApostaRequest;
import rasbetUI.Game;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IRasbetLN {

    void validateLogin (String email, String password) throws SQLException;
    void addGame(Game game, String bookmaker, String desporto) throws SQLException;

    // Class Apostador
    void addFavorito(String id,String desporto,String f);

    void apostaSimples(ApostaRequest apostaRequest);
    void apostaMultipla(ApostaRequest apostaRequest);

    void deposito(String userId, float valor) throws SQLException;

    void levantamento(String userId, float valor)throws SQLException;

    void registarApostador(String email, String password,String nome, String nif, LocalDate date, String morada, String telemovel) throws SQLException;
}
