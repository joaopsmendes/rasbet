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
import java.time.LocalDateTime;
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
        Jogo jogo = new Jogo(game.getId(),d, game.getHoraComeco(),game.getTitulo(), Jogo.Estado.ATIVO);
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
    public void addGame(GameOutput game) throws SQLException {
        Desporto d = mapDesportos.get(game.getDesporto());
        Jogo jogo = new Jogo(game.getIdJogo(),d, game.getData(),game.getTitulo(), Jogo.Estado.ATIVO);
        List<ApostaOutput> listApostas = game.getMapMercados().get(game.getEscolhido());
        for (ApostaOutput aposta : listApostas){
            for (Outcome outcome : aposta.getOdds()){
                System.out.println(outcome.getName());
                Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo.getIdJogo());
                jogo.addOdd(aposta.getTema(),odd);
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
        float montante  = apostaRequest.getValor();
        float freebets = apostaRequest.getFreebets();
        float saldo = apostaRequest.getSaldo();
        float ganhos = apostaRequest.getGanhoPossivel();
        List<Integer> listOdds = List.of(apostaRequest.getOdds());


        gestaoUtilizadores.updateSaldoFreebets(userId,saldo,freebets);
        gestaoApostas.createAposta(userId,montante,ganhos,listOdds);
        Transacao transacao = new Transacao(saldo*-1,freebets*-1,LocalDateTime.now(), Transacao.Tipo.APOSTA);
        gestaoUtilizadores.transacao(userId,transacao);
    }

    @Override
    public List<Aposta> historicoApostas(String userId) throws SQLException {
        return gestaoApostas.getHistoricoApostas(userId);
    }

    @Override
    public List<Transacao> historicoTransacoes(String userId) throws SQLException {
        return gestaoUtilizadores.getHistTransacoes(userId);
    }


    @Override
    public void alterarOdd(int idOdd, float valor) throws SQLException {
        gestaoJogos.alterarOdd(idOdd,valor);
    }

    public void deposito(String userId, float valor) throws SQLException {
        Transacao transacao = new Transacao(valor,0, LocalDateTime.now(), Transacao.Tipo.DEPOSITO);
        gestaoUtilizadores.updateSaldo(userId,valor);
        gestaoUtilizadores.transacao(userId,transacao);
    }

    public void levantamento(String userId, float valor) throws SQLException{
        Transacao transacao = new Transacao(valor*-1,0, LocalDateTime.now(), Transacao.Tipo.LEVANTAMENTO);
        gestaoUtilizadores.updateSaldo(userId,valor*-1);
        gestaoUtilizadores.transacao(userId,transacao);
    }


    public void validateLogin (String email, String password) throws SQLException {
        gestaoUtilizadores.logIn(email,password);
    }

    public void registarApostador(String email, String password, String nome,String nif,LocalDate date, String morada, String telemovel) throws SQLException {
        Apostador apostador = new Apostador(email,password,date,nif,nome,telemovel,morada);
        gestaoUtilizadores.newApostador(apostador);
        Transacao transacao = new Transacao(0,5,LocalDateTime.now(), Transacao.Tipo.CRIACAO_CONTA);
        gestaoUtilizadores.transacao(email,transacao);
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
        Map<Integer,List<Integer>> res = gestaoJogos.updateResultados(map,d);
        Map<String,Float> mapSaldos = gestaoApostas.updateResultados(res);
        for ( Map.Entry<String, Float> entry: mapSaldos.entrySet()){
            gestaoUtilizadores.updateSaldo(entry.getKey(),entry.getValue());
        }
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
        if (dados.containsKey("username")){
            String name = dados.get("username");
            user.setNome(name);
        }

        if (dados.containsKey("password")){
            String pass = dados.get("password");
            user.setPassword(pass);
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

    @Override
    public Map<String, Float> getSaldo(String userId) throws SQLException {
        return gestaoUtilizadores.getSaldoFreeBets(userId);
    }

    @Override
    public Map<String, String> infoUser(String userId) throws SQLException{
        return gestaoUtilizadores.info(userId);
    }

    @Override
    public void alterarEstado(String idJogo, int estado) throws SQLException {
        gestaoJogos.alteraEstado(idJogo,estado);
    }



}
