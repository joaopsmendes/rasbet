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
import java.util.*;

public class RasbetLN implements IRasbetLN{

    private Map<String, Desporto> mapDesportos;
    private IGestaoJogos gestaoJogos;
    private IGestaoUtilizadores gestaoUtilizadores;
    private IGestaoApostas gestaoApostas;

    static double BONUS_PERCENTAGEM = 0.05;
    static int BONUS_STREAK = 5;
    static int BONUS_MAX = 10;

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
        Jogo jogo = new Jogo(game.getId(),d, game.getHoraComeco(),game.getTitulo(), Jogo.Estado.ATIVO,game.getParticipantes());
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
        Jogo jogo = new Jogo(game.getIdJogo(),d, game.getData(),game.getTitulo(), Jogo.Estado.ATIVO,game.getParticipantes());
        List<ApostaOutput> listApostas = game.getMapMercados().get(game.getEscolhido());
        for (ApostaOutput aposta : listApostas){
            for (Outcome outcome : aposta.getOdds()){
                System.out.println(outcome.getName());
                Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo.getIdJogo());
                jogo.addOdd(aposta.getTema(),odd);
            }
        }

        gestaoJogos.adicionarJogo(jogo);
        for(String participante : game.getParticipantes()) {
            for(String user : gestaoUtilizadores.getUtilizadoresFav(participante, d)) {
                String str = "Foi adicionado um jogo da equipa " + participante + " dos seus favoritos.";
                sendNotificacaoUtilizador(user, str);
            }
        }
    }

    @Override
    public void addFavorito(String sessionId,String desporto,String f) throws SQLException {
        String idUser = gestaoUtilizadores.getUserid(sessionId);
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        gestaoUtilizadores.addFavorito(idUser,fav);
    }

    public void removeFavorito(String sessionId,String desporto,String f) throws SQLException {
        String idUser = gestaoUtilizadores.getUserid(sessionId);
        Desporto d = mapDesportos.get(desporto);
        Favorito fav = new Favorito(f,d);
        gestaoUtilizadores.removeFavorito(idUser,fav);
    }

    public void aposta(ApostaRequest apostaRequest) throws SQLException {

        String sessionId = apostaRequest.getSessionId();
        String userId = gestaoUtilizadores.getUserid(sessionId);
        float montante  = apostaRequest.getValor();
        float freebets = apostaRequest.getFreebets();
        float saldo = apostaRequest.getSaldo();
        float ganhos = apostaRequest.getGanhoPossivel();
        List<Integer> listOdds = List.of(apostaRequest.getOdds());


        gestaoUtilizadores.updateSaldoFreebets(userId,saldo*-1,freebets*-1);
        gestaoApostas.createAposta(userId,montante,ganhos,listOdds);
        Transacao transacao = new Transacao(saldo*-1,freebets*-1,LocalDateTime.now(), Transacao.Tipo.APOSTA);
        gestaoUtilizadores.transacao(userId,transacao);
    }

    @Override
    public List<Aposta> historicoApostas(String sessionId) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoApostas.getHistoricoApostas(userId);
    }

    @Override
    public List<Transacao> historicoTransacoes(String sessionId) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoUtilizadores.getHistTransacoes(userId);
    }

//    public List<Integer> getOddsJogo(int idOdd) throws SQLException{
       //        return gestaoJogos.getOddsJogo(idOdd);
       //    }



    @Override
    public void addJogoASeguir(String idJogo, String desporto, String sessionId) throws SQLException {
        Desporto sport = this.mapDesportos.get(desporto);
        String idUser = gestaoUtilizadores.getUserid(sessionId);
        this.gestaoUtilizadores.addJogoASeguir(idJogo, sport.getIdDesporto(), idUser);
    }

    public void removeJogoASeguir(String idJogo, String desporto, String sessionId) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        Desporto sport = this.mapDesportos.get(desporto);
        this.gestaoUtilizadores.removeJogoASeguir(idJogo, sport.getIdDesporto(), userId);
    }

    @Override
    public void alterarOdd(int idOdd, float valor) throws SQLException {
        Set<String> utilizadores = new HashSet<>();
        gestaoJogos.alterarOdd(idOdd,valor);

        String idJogo = gestaoJogos.getIdJogoFromOdd(idOdd);
        String tituloJogo = gestaoJogos.getTituloJogo(idJogo);
        // Enviar not aos utilizadores
        for(Integer i : gestaoJogos.getOddsJogo(idJogo)) {
            utilizadores.addAll(gestaoApostas.getUtilizadoresOdd(i));
        }

        utilizadores.addAll(gestaoUtilizadores.getUtilizadoresJogoASeguir(idJogo));

        List<String> list = new ArrayList<>(utilizadores);
        sendNotificacaoUtilizadores(list, "A Odd do jogo" + tituloJogo + "foi alterada");
    }

    public void deposito(String sessionId, float valor, int promocao) throws SQLException {
        deposito(sessionId,valor);
        String userId = gestaoUtilizadores.getUserid(sessionId);

        int freebets = gestaoUtilizadores.getValorPromocaoDeposito(promocao);
        gestaoUtilizadores.updateSaldoFreebets(userId,0,freebets);
        Transacao transacao = new Transacao(0,freebets,LocalDateTime.now(), Transacao.Tipo.PROMOCAO);
        gestaoUtilizadores.transacao(userId,transacao);

    }

    public void deposito(String sessionId, float valor) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);

        gestaoUtilizadores.updateSaldo(userId,valor);

        Transacao transacao = new Transacao(valor,0, LocalDateTime.now(), Transacao.Tipo.DEPOSITO);
        gestaoUtilizadores.transacao(userId,transacao);

    }

    public void levantamento(String sessionId, float valor) throws SQLException{
        Transacao transacao = new Transacao(valor*-1,0, LocalDateTime.now(), Transacao.Tipo.LEVANTAMENTO);
        String userId = gestaoUtilizadores.getUserid(sessionId);
        gestaoUtilizadores.updateSaldo(userId,valor*-1);
        gestaoUtilizadores.transacao(userId,transacao);
    }


    public Map<String, String> validateLogin (String email, String password) throws SQLException {
        return gestaoUtilizadores.logIn(email,password);
    }

    public String registarApostador(String email, String password, String nome,String nif,LocalDate date, String morada, String telemovel) throws SQLException {
        Apostador apostador = new Apostador(email,password,date,nif,nome,telemovel,morada);
        String sessionId = gestaoUtilizadores.newApostador(apostador);
        Transacao transacao = new Transacao(0,5,LocalDateTime.now(), Transacao.Tipo.CRIACAO_CONTA);
        gestaoUtilizadores.transacao(email,transacao);

        String conteudo = "Bem-vindo/a " + nome + ", aproveite já os 5 freebets disponíveis.";
        sendNotificacaoUtilizador(email, conteudo);
        return sessionId;
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
        Map<String,List<Float>> mapSaldos = gestaoApostas.updateResultados(res);
        for (Map.Entry<String, List<Float>> entry: mapSaldos.entrySet()){
            for (float valor : entry.getValue()){
                String str = "Parabéns! Ganhou uma aposta no valor de " + valor + "€." ;
                sendNotificacaoUtilizador(entry.getKey(), str);

                Transacao t = new Transacao(valor,0,LocalDateTime.now(), Transacao.Tipo.GANHO_APOSTA);

                gestaoUtilizadores.transacao(entry.getKey(),t);
                gestaoUtilizadores.updateSaldo(entry.getKey(),valor);

                int streak = gestaoUtilizadores.updateStreak(entry.getKey(),valor);
                if (streak == BONUS_STREAK){

                    float bonus = (float) (gestaoUtilizadores.bonusStreak(entry.getKey()) * BONUS_PERCENTAGEM);
                    if (bonus > BONUS_MAX) bonus = BONUS_MAX;
                    if (bonus > BONUS_MAX) bonus = BONUS_MAX;
                    String s = "Parabéns! Ganhou" + BONUS_STREAK + "apostas no valor de " + bonus;
                    sendNotificacaoUtilizador(entry.getKey(), s);
                    Transacao transacao = new Transacao(0,bonus,LocalDateTime.now(), Transacao.Tipo.BONUS_SEGUIDAS);
                    gestaoUtilizadores.transacao(entry.getKey(),transacao);
                }

            }
        }

    }

    public void addNotificacao(String sessionId, String conteudo) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        Notificacao notificacao = new Notificacao(conteudo,false);
        gestaoUtilizadores.addNotificacao(userId,notificacao);
    }

    public List<Notificacao> getNotifications(String sessionId) throws SQLException{
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoUtilizadores.getNotificacao(userId);
    }

    public List<Favorito> getFavorites(String sessionId) throws SQLException{
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoUtilizadores.getFavoritos(userId);
    }

    @Override
    public void fecharAposta(String sessionId,int idAposta, boolean resultado) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        float value = gestaoApostas.fecharAposta(idAposta,resultado);
        if(resultado){
            gestaoUtilizadores.updateSaldo(userId,value);
        }
    }

    public void alterarPerfil(String sessionId, Map<String, String> dados) throws SQLException{
        String userId = gestaoUtilizadores.getUserid(sessionId);
        Utilizador user = gestaoUtilizadores.getByEmail(userId);
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
        gestaoUtilizadores.replace(userId, user);
    }

    public void cashout(int idAposta,String sessionId) throws SQLException {
        float montante = gestaoApostas.cashout(idAposta);
        String userId = gestaoUtilizadores.getUserid(sessionId);
        gestaoUtilizadores.updateSaldoFreebets(userId,0,montante);
        Transacao transacao = new Transacao(0,montante,LocalDateTime.now(), Transacao.Tipo.CASHOUT);
        gestaoUtilizadores.transacao(userId,transacao);
    }

    public List<Desporto> getDesporto(){
        ArrayList<Desporto> desportos = new ArrayList<>();
        for (Map.Entry<String,Desporto> entry : mapDesportos.entrySet()){
            desportos.add(entry.getValue());
        }
        return desportos;
    }

    @Override
    public Map<String, Float> getSaldo(String sessionId) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoUtilizadores.getSaldoFreeBets(userId);
    }

    @Override
    public Map<String, String> infoUser(String sessionId) throws SQLException{
        String userId = gestaoUtilizadores.getUserid(sessionId);
        return gestaoUtilizadores.info(userId);
    }

    @Override
        public void suspenderJogo(String idJogo) throws SQLException {
        gestaoJogos.suspenderJogo(idJogo);
    }

    @Override
    public void ativarJogo(String idJogo) throws SQLException {
        gestaoJogos.ativarJogo(idJogo);
    }

    @Override
    public void updateEstadoJogos() throws SQLException {
        gestaoJogos.updateEstadoJogos();
    }



    public Map<String, List<String>> getJogosASeguir(String sessionId) throws SQLException{
        String userId = gestaoUtilizadores.getUserid(sessionId);
        Map<Integer, List<String>> mapa = gestaoUtilizadores.getJogosASeguir(userId);
        Map<String, List<String>> novoMapa = new HashMap<>();

        for (Map.Entry<Integer, List<String>> entry : mapa.entrySet()){
            for (Map.Entry<String, Desporto> entry2 : mapDesportos.entrySet()){
                if (entry2.getValue().getIdDesporto() == entry.getKey()){
                    novoMapa.putIfAbsent(entry2.getKey(), entry.getValue());
                }
            }
        }

        return novoMapa;
    }


    public void sendNotificacao(Notificacao notificacao) throws SQLException{
        gestaoUtilizadores.sendNotificacao(notificacao);

    }

    public void sendNotificacao(String conteudo) throws SQLException{
        gestaoUtilizadores.sendNotificacao(conteudo);

    }

    @Override
    public void removeNotificacao(String sessionId, int idNotificacao) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        gestaoUtilizadores.removeNotificacao(userId, idNotificacao);
    }

    @Override
    public void vistaNotificacao(String sessionId, int notificacao) throws SQLException {
        String userId = gestaoUtilizadores.getUserid(sessionId);
        gestaoUtilizadores.vistaNotificacao(userId, notificacao);
    }

    public void sendNotificacaoUtilizador(String email, String conteudo) throws SQLException {
        gestaoUtilizadores.sendNotificacaoUtilizador(email, conteudo);
    }

    private void sendNotificacaoUtilizadores(List<String> utilizadores, String conteudo) throws SQLException {
        for(String userId : utilizadores) {
            sendNotificacaoUtilizador(userId, conteudo);
        }
    }


    public List<Promocao> getPromoApostaSegura(String userId) throws SQLException{
        return gestaoUtilizadores.getPromoApostaSegura(userId);
    }

    public List<Promocao> getPromoFreeBetsDeposito(String userId) throws SQLException{
        return gestaoUtilizadores.getPromoFreeBetsDeposito(userId);
    }

    public List<String> getUsersPromocao(int promoId) throws SQLException{
        return gestaoUtilizadores.getUsersPromocao(promoId);
    }

    public void createPromocaoApostaSegura(int limite) throws SQLException{
        gestaoUtilizadores.createPromocaoApostaSegura(limite);

        String conteudo = "Recebeu uma Aposta Segura até " + limite + "€";
        sendNotificacao(conteudo);
        //sendNotificacaoUtilizadores(gestaoUtilizadores.getIdApostadores(), conteudo);
    }

    public void createPromocaoFreeBetsDeposito(int deposito, int freeBets) throws SQLException{
        gestaoUtilizadores.createPromocaoFreebetsAposDeposito(deposito, freeBets);
        String conteudo = "Receba " + freeBets + " FreeBets caso faça um depóstio superior a " + deposito + " €";
        sendNotificacao(conteudo);
        //sendNotificacaoUtilizadores(gestaoUtilizadores.getIdApostadores(), conteudo);
    }
}
