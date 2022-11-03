package rasbetLN;

import rasbetLN.GestaoApostas.GestaoApostas;
import rasbetLN.GestaoApostas.IGestaoApostas;
import rasbetLN.GestaoApostas.Odd;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.GestaoJogos;
import rasbetLN.GestaoJogos.IGestaoJogos;
import rasbetLN.GestaoJogos.Jogo;
import rasbetLN.GestaoUtilizadores.*;
import rasbetUI.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RasbetLN implements IRasbetLN{

    private Map<String, Desporto> mapDesportos;
    private IGestaoJogos gestaoJogos;
    private IGestaoUtilizadores gestaoUtilizadores;
    private IGestaoApostas gestaoApostas;

    public RasbetLN() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost/rasbet","root","root");
        gestaoJogos = new GestaoJogos(conn);
        gestaoUtilizadores = new GestaoUtilizadores(conn);
        gestaoApostas = new GestaoApostas(conn);
        mapDesportos = gestaoJogos.getDesportos();
    }

    @Override
    public void addGame(GameFutebol gameFutebol, String bookmaker, String desporto) throws SQLException{
        Desporto d = mapDesportos.get(desporto);
        Jogo jogo = new Jogo(gameFutebol.getId(),d, gameFutebol.getHoraComeco());
        for (Market market : gameFutebol.getMarkets(bookmaker)){
            for (Outcome outcome : market.getOutcomes()){
                Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo.getIdJogo());
                jogo.addOdd(market.getKey(),odd);
            }
        }

        gestaoJogos.adicionarJogo(jogo);
        // Adicionar à estrutura e BD

    }

    @Override
    public void addFavorito(String id,String desporto,String f) throws SQLException {
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        gestaoUtilizadores.addFavorito(id,fav);
    }

    public void removeFavorito(String id,String desporto,String f) throws SQLException {
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        gestaoUtilizadores.removeFavorito(id,fav);
    }



    @Override
    public void apostaSimples(ApostaRequest apostaRequest) throws SQLException {
        //Apostador apostador = (Apostador) gestaoUtilizadores.getByEmail(apostaRequest.getUserId());
       // ApostaOpcao apostaOpcao = apostaRequest.getOpcoes()[0];

        String userId = apostaRequest.getUserId();
        float montante = apostaRequest.getValor();
        int idOdd = apostaRequest.getOdds()[0];

        gestaoApostas.createSimples(userId,montante,idOdd);
        gestaoUtilizadores.fazerAposta(userId,montante);
        //apostador.addAposta(simples);

        //Add to DATABASE

    }

    public void apostaMultipla(ApostaRequest apostaRequest) throws SQLException {

        String userId = apostaRequest.getUserId();
        float montante = apostaRequest.getValor();
        List<Integer> listOdds = List.of(apostaRequest.getOdds());

        gestaoApostas.createMultipla(userId,montante,listOdds);
        gestaoUtilizadores.fazerAposta(userId,montante);
    }

    public void deposito(String userId, float valor) throws SQLException {
        Deposito deposito = new Deposito(valor);
        gestaoUtilizadores.deposito(userId,deposito);
    }

    public void levantamento(String userId, float valor) throws SQLException{
        Levantamento levantamento = new Levantamento(valor);
        gestaoUtilizadores.levantamento(userId, levantamento);
    }


    public void validateLogin (String email, String password) throws SQLException {
        gestaoUtilizadores.logIn(email,password);
    }

    public void registarApostador(String email, String password, String nome,String nif,LocalDate date, String morada, String telemovel) throws SQLException {
        Apostador apostador = new Apostador(email,password,date,nif,nome,morada,telemovel);
        gestaoUtilizadores.newApostador(apostador);
    }

    @Override
    public boolean existsGame(String gameId, String desporto) throws SQLException {
        Desporto d = mapDesportos.get(desporto);
        return gestaoJogos.existeJogo(gameId,d);
    }

    public Map<String,Jogo> getJogos(String desporto) throws SQLException {
        Desporto d = mapDesportos.get(desporto);
        return gestaoJogos.getJogos(d);
    }

    @Override
    public void updateResultados(Map<String, String> map,String desporto) throws SQLException {
        Desporto d = mapDesportos.get(desporto);
        gestaoJogos.updateResultados(map,d);
    }

    public void addNotificacao(String userId, String conteudo) throws SQLException {
        Notificacao notificacao = new Notificacao(conteudo,false);
        gestaoUtilizadores.addNotificacao(userId,notificacao);
    }


    public List<Favorito> getFavorites(String userId) throws SQLException{
        return gestaoUtilizadores.getFavoritos(userId);
    }

}
