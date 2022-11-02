package rasbetLN;

import rasbetUI.ApostaRequest;
import rasbetUI.Game;
import rasbetUI.Market;
import rasbetUI.Outcome;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class RasbetLN implements IRasbetLN{

    private Map<Integer,Desporto> mapDesportos;
    private IGestaoJogos gestaoJogos;
    private IGestaoUtilizadores gestaoUtilizadores;


    public RasbetLN(){
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost/rasbet","root","root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gestaoJogos = new GestaoJogos(conn);
        gestaoUtilizadores = new GestaoUtilizadores(conn);
        try {
            mapDesportos = gestaoJogos.getDesportos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addGame(Game game, String bookmaker, String desporto) throws SQLException{
        Desporto d = mapDesportos.get(desporto);
        Jogo jogo = new Jogo(game.getId(),d,game.getCommenceTime());
        for (Market market : game.getMarkets(bookmaker)){
            for (Outcome outcome : market.getOutcomes()){
                Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo);
                jogo.addOdd(market.getKey(),odd);
            }
        }

        gestaoJogos.adicionarJogo(jogo);
        // Adicionar à estrutura e BD

    }

    @Override
    public void addFavorito(String id,String desporto,String f) {
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        //gestaoUtilizadores.addFavorito(id,fav);
    }

    public void removeFavorito(String id,String desporto,String f) {
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        //gestaoUtilizadores.removeFavorito(id,fav);
    }



    @Override
    public void apostaSimples(ApostaRequest apostaRequest) {
        //Apostador apostador = (Apostador) gestaoUtilizadores.getByEmail(apostaRequest.getUserId());
        //ApostaOpcao apostaOpcao = apostaRequest.getOpcoes()[0];

        //Jogo jogo = jogos.get(apostaOpcao.getDesporto()).get(apostaOpcao.getGameId());
        //Odd odd = jogo.getOdd(apostaOpcao.getOpcao());

        //Simples simples = new Simples(apostaRequest.getValor(),odd);
        //apostador.addAposta(simples);

        //Add to DATABASE

    }

    public void apostaMultipla(ApostaRequest apostaRequest){
        //Apostador apostador = (Apostador) gestaoUtilizadores.getByEmail(apostaRequest.getUserId());
        //List<Odd> listOdd = new ArrayList<>();
        //for(ApostaOpcao apostaOpcao : apostaRequest.getOpcoes()){
        //    Jogo jogo = jogos.get(apostaOpcao.getDesporto()).get(apostaOpcao.getGameId());
        //    Odd odd = jogo.getOdd(apostaOpcao.getOpcao());
        //    listOdd.add(odd);
        //}
        //Multipla multipla = new Multipla(apostaRequest.getValor(),listOdd);
        //apostador.addAposta(multipla);

        //ADD TO DATABASE
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




}
