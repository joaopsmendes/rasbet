package rasbetLN;

import rasbetLN.GestaoApostas.Aposta;
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
import java.util.ArrayList;
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
    public void addGame(Game game, String bookmaker, String desporto) throws SQLException{
        Desporto d = mapDesportos.get(desporto);
        Jogo jogo = new Jogo(game.getId(),d, game.getHoraComeco());
        Map<String,List<Outcome>> mapOdds = game.getOdds(bookmaker);
        for (Map.Entry<String, List<Outcome>> entry :mapOdds.entrySet()){
            for (Outcome outcome : entry.getValue()){
                Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo.getIdJogo());
                jogo.addOdd(entry.getKey(),odd);
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



    public void aposta(ApostaRequest apostaRequest) throws SQLException {

        String userId = apostaRequest.getUserId();
        float montante = apostaRequest.getValor();
        List<Integer> listOdds = List.of(apostaRequest.getOdds());

        gestaoUtilizadores.updateSaldo(userId,montante*-1);
        gestaoApostas.createAposta(userId,montante,listOdds);
    }

    @Override
    public void historicoApostas(String userId) throws SQLException {
        List<Aposta> historicoApostas = gestaoApostas.getHistoricoApostas(userId);
    }

    @Override
    public void alterarOdd(int idOdd, float valor) throws SQLException {
        gestaoJogos.alterarOdd(idOdd,valor);
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

    @Override
    public void fecharAposta(String userId,int idAposta, boolean resultado) throws SQLException {
        float value = gestaoApostas.fecharAposta(idAposta,resultado);
        if(resultado){
            gestaoUtilizadores.updateSaldo(userId,value);
        }
    }

    public void alterarPerfil(String userID, Map<String, String> dados) throws SQLException{
        Utilizador user = gestaoUtilizadores.getByEmail(userID);
        if (dados.containsKey("nome")){
            String name = dados.get("nome");
            user.setNome(name);
        }

        if (dados.containsKey("email")){
            String email = dados.get("email");
            user.setEmail(email);
        }

        if (dados.containsKey("telemovel")){
            String tele = dados.get("telemovel");
            user.setTelemovel(tele);
        }

        if (dados.containsKey("morada")){
            String mrd = dados.get("morada");
            user.setMorada(mrd);
        }
        gestaoUtilizadores.replace(userID, user);
    }

    public void cashout(int idAposta,String userId) throws SQLException {
        float montante = gestaoApostas.cashout(idAposta);
        gestaoUtilizadores.updateSaldo(userId,montante);

    }

    public List<Desporto> getDesporto(){
        ArrayList<Desporto> desportos = new ArrayList<>();
        for (Map.Entry<String,Desporto> entry : mapDesportos.entrySet()){
            desportos.add(entry.getValue());
        }
        return desportos;
    }

}
