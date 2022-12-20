package rasbetDB;

import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoUtilizadores.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBUtilizadores {
    private Connection c;

    public DBUtilizadores(Connection connection) {
        this.c = connection;
    }

    public void createApostador(Apostador apostador) throws SQLException {

        createUtilizador(apostador);

        String query2 = "INSERT INTO Apostador(Utilizador_email,seguidas,ganhos)VALUES(?,0,0)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, apostador.getEmail());
        pstmt2.execute();

        criarCarteira(apostador.getEmail());
    }

    public void createUtilizador(Utilizador utilizador) throws SQLException {
        String query1 = "INSERT INTO Utilizador(email, dataNascimento, NIF, pass, nome, telemovel, morada)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1);
        pstmt1.setString(1, utilizador.getEmail());
        pstmt1.setDate(2, Date.valueOf(utilizador.getDataNascimento()));
        pstmt1.setString(3, utilizador.getNIF());
        pstmt1.setString(4, utilizador.getPassword());
        pstmt1.setString(5, utilizador.getNome());
        pstmt1.setString(6, utilizador.getTelemovel());
        pstmt1.setString(7, utilizador.getMorada());
        pstmt1.execute();

    }

    public void criarCarteira(String userId) throws SQLException {
        String query1 = "INSERT INTO Carteira(Utilizador_email,saldo,freebets)VALUES(?,?,?)";
        PreparedStatement st = c.prepareStatement(query1);
        st.setString(1, userId);
        st.setFloat(2,0);
        st.setFloat(3,5);
        st.execute();

    }


    private boolean isTipo(String tipo, String email) throws SQLException{
        String query = "SELECT * FROM "+ tipo + " WHERE Utilizador_email = ?" ;
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    private String getTipoUtilizador(String email) throws SQLException {
        if (isTipo("Apostador",email)) return "Apostador";
        if (isTipo("Especialista",email)) return "Especialista";
        return "Adminstrador";
    }

    public String logIn(String email, String password) throws SQLException {
        String query = "SELECT email, pass FROM Utilizador WHERE email = ? AND pass = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Utilizador não existe");
        } else {
            return getTipoUtilizador(email);
        }
    }


    public Utilizador getUtilizador(String email) throws SQLException {
        String query = "SELECT nome, pass, dataNascimento, NIF, Telemovel,Morada FROM Utilizador"
                + " WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nome = rs.getString("nome");
            String password = rs.getString("pass");
            LocalDate dataNascimento = LocalDate.parse(rs.getString("dataNascimento"));
            String nif = rs.getString("NIF");
            String telemovel = rs.getString("Telemovel");
            String morada = rs.getString("morada");
            return new Apostador(email, password, dataNascimento, nif, nome, telemovel, morada);
        }
        throw new SQLException("Can't get user");
    }

//    public void replacePassword(String email, String password) throws SQLException{
//        String query = "UPDATE utilizador SET Pass = ? WHERE Email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1,password);
//        ps.setString(2,email);
//        ps.execute();
//    }

//    public void replaceNome(String email, String nome) throws SQLException {
//        String query = "UPDATE utilizador SET Nome = ?WHERE Email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, nome);
//        ps.setString(2, email);
//        ps.execute();
//    }
//
//    public void replaceEmail(String email, String nif) throws SQLException {
//        String query = "UPDATE utilizador SET Email = ? WHERE nif = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, email);
//        ps.setString(2, nif);
//        ps.execute();
//    }
//
//
//    public void replaceTelemovel(String email, String telemovel) throws SQLException {
//        String query = "UPDATE utilizador SET telemovel = ? WHERE email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, telemovel);
//        ps.setString(2, email);
//        ps.execute();
//    }
//
//    public void replaceMorada(String email, String morada) throws SQLException {
//        String query = "UPDATE utilizador SET morada = ? WHERE email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, morada);
//        ps.setString(2, email);
//        ps.execute();
//    }

    public void replaceUtilizador(Utilizador user, String oldEmail) throws SQLException{
        String query = "UPDATE Utilizador SET nome = ?, pass = ?, telemovel = ?, morada = ?, email = ? WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, user.getNome());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getTelemovel());
        ps.setString(4, user.getMorada());
        ps.setString(5, user.getEmail());
        ps.setString(6, oldEmail);
        ps.execute();

    }

// Não sei se é preciso
    public Utilizador logOut(String email) throws SQLException {
        String query = "SELECT Utilizador.email FROM Utilizador WHERE email = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        int i = ps.executeUpdate();
        if (i == 0) {
            System.out.println("Logout falhado");
        } else System.out.println("Logout efetuado");
        return null;
    }


    public void addFavorito(String userId, Favorito favorito) throws SQLException {
        String query = "INSERT INTO Favorito(Participante_idParticipante, Utilizador_email, Participante_Desporto_idDesporto)VALUES (?,?,?) ";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, favorito.getNome());
        pstmt.setString(2, userId);
        pstmt.setInt(3, favorito.getDesporto().getIdDesporto());
        pstmt.execute();
    }

    public void removeFavorito(String userId, Favorito favorito) throws SQLException {
        System.out.println("REMOVE");
        String query = "DELETE FROM Favorito WHERE Participante_idParticipante = ? AND  Utilizador_email=? AND Participante_Desporto_idDesporto = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, favorito.getNome());
        pstmt.setString(2, userId);
        pstmt.setInt(3, favorito.getDesporto().getIdDesporto());
        pstmt.execute();
    }

    public float getSaldo(String userId) throws SQLException {
        String query = "SELECT saldo FROM Carteira WHERE Utilizador_email = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getFloat("saldo");
        }
        throw new SQLException("Saldo não encontrado.");
    }

    public Map<String,Float> getSaldoFreeBets(String userId) throws SQLException {
        String query = "SELECT saldo,freebets FROM Carteira WHERE Utilizador_email = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        Map<String,Float> map = new HashMap<>();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            float saldo = rs.getFloat("saldo");
            float freebets = rs.getFloat("freebets");
            map.put("saldo",saldo);
            map.put("freebets",freebets);
            return map;
        }
        throw new SQLException("Saldo não encontrado.");
    }


    public void updateSaldo(float saldo, String userId) throws SQLException {
        String query = "UPDATE Carteira SET saldo = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, saldo);
        ps.setString(2, userId);
        ps.execute();
    }

    public void updateFreebets(float freebets, String userId) throws SQLException {
        String query = "UPDATE Carteira SET freebets = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, freebets);
        ps.setString(2, userId);
        ps.execute();
    }

    public void updateSaldoFreebets(float saldo,float freebets, String userId) throws SQLException {
        String query = "UPDATE Carteira SET freebets = ?, saldo = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, freebets);
        ps.setFloat(2, saldo);
        ps.setString(3, userId);
        ps.execute();
    }



    public void novaTransacao(Transacao transacao,String userId) throws SQLException {
        String query = "INSERT INTO Transacao(saldo, freebets, dataTransacao, Utilizador_Email,TipoTransacao_idTipoTransacao)VALUES (?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, transacao.getSaldo());
        ps.setFloat(2, transacao.getFreebets());
        ps.setString(3, transacao.getData().toString());
        ps.setString(4, userId);
        ps.setInt(5,transacao.getTipo().value);
        ps.execute();

    }


    public List<Transacao> getHistTransacoes(String userId) throws SQLException{
        List<Transacao> list = new ArrayList<>();
        String query = "SELECT * FROM Transacao WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while(rs.next()){
            LocalDateTime dataTransacao = LocalDateTime.parse(rs.getString("dataTransacao"),formatter);
            float saldo = rs.getFloat("saldo");
            float freebets = rs.getFloat("freebets");
            int tipoIndex = rs.getInt("TipoTransacao_idTipoTransacao");
            Transacao transacao = new Transacao(saldo,freebets,dataTransacao, Transacao.Tipo.values()[tipoIndex]);
            list.add(transacao);


        }
        return list;
    }

    public void addNotificacao(String userId, Notificacao notificacao) throws SQLException{
        String query = "INSERT INTO Notificacao(idNotificacao, conteudo, vista, Utilizador_email)VALUES (?,?,?,?) ";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, notificacao.getIdNotificacao());
        pstmt.setString(2, notificacao.getConteudo());
        pstmt.setBoolean(3, false);
        pstmt.setString(4, userId);
        pstmt.execute();
    }


    public void removeNotificacao(String userId, int notificacao) throws SQLException{
        String query = "DELETE FROM Notificacao WHERE idNotificacao = ? AND Utilizador_email = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, notificacao);
        pstmt.setString(2, userId);
        pstmt.execute();

    }

    public void vistaNotificacao(String userId, int notificacao) throws SQLException{
        String query = "UPDATE Notificacao SET vista = 1 WHERE idNotificacao = ? AND Utilizador_email = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, notificacao);
        pstmt.setString(2, userId);
        pstmt.execute();
    }

    public List<Notificacao> getListaNotificacao(String userId) throws SQLException{
        List<Notificacao> lista = new ArrayList<>();
        String query = "SELECT * FROM Notificacao Where Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idNotificacao = rs.getInt("idNotificacao");
            String conteudo = rs.getString("conteudo");
            boolean vista = rs.getBoolean("vista");
            Notificacao noti = new Notificacao( idNotificacao,conteudo, vista);
            lista.add(noti);
        }
        return lista;
    }

    public List<Favorito> getFavoritos(String userId) throws SQLException{
        List<Favorito> lista = new ArrayList<>();
        String query ="SELECT * FROM Favorito INNER JOIN Desporto ON Participante_Desporto_idDesporto=idDesporto Where Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String favorito = rs.getString("Participante_idParticipante");
            int idDesporto = rs.getInt("Participante_Desporto_idDesporto");
            String modalidade = rs.getString("modalidade");
            Desporto desp = new Desporto(idDesporto, modalidade);
            Favorito fav = new Favorito(favorito, desp);
            lista.add(fav);
        }
        return lista;
    }

    public Map<String, String> info(String userId) throws SQLException{
        Map<String,String> map = new HashMap<>();
        String query ="SELECT * FROM Utilizador Where email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            map.put("date",rs.getString("dataNascimento"));
            map.put("nif",rs.getString("NIF"));
            map.put("username",rs.getString("nome"));
            map.put("telemovel",rs.getString("Telemovel"));
            map.put("morada",rs.getString("Morada"));
        }
        return map;
    }

    public int updateStreak(String id, float valor) throws SQLException{
        String query ="SELECT * FROM Apostador Where Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int streak = rs.getInt("seguidas");
            float ganhos = rs.getFloat("ganhos");
            streak++;
            String query2 = "UPDATE Apostador SET seguidas = ?, ganhos = ? WHERE Utilizador_email=?";
            PreparedStatement ps2 = c.prepareStatement(query2);
            ps2.setInt(1,streak);
            ps2.setFloat(2,ganhos);
            ps2.setString(3,id);
            ps2.executeUpdate();
            return streak;
        }
        throw new SQLException("Apostador não encontrado");
// TODO Verificar se ganhou 5, caso sim -> dar o bonus e dar reset aos ganhos e seguidas
    }

    public float bonusStreak(String id) throws SQLException {
        String query ="SELECT * FROM Apostador Where Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            float ganhos = rs.getFloat("ganhos");
            String query2 = "UPDATE Apostador SET seguidas = 0, ganhos = 0 WHERE Utilizador_email=?";
            PreparedStatement ps2 = c.prepareStatement(query2);
            ps2.setString(1,id);
            ps2.executeUpdate();
            return ganhos;
        }
        throw new SQLException("Apostador não encontrado");
    }

    public List<String> getIdApostadores() throws SQLException{
        List<String> lista = new ArrayList<>();
        String query ="SELECT Utilizador_email FROM Apostador";
        PreparedStatement ps  = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String email = rs.getString("Utilizador_email");
            lista.add(email);
        }
        return lista;
    }

    public Set<String> getUtilizadoresOddS(int idOdd) throws SQLException{
        Set<String> listaUtilizadores = new HashSet<>();
        String query = "SELECT Utilizador_email FROM Simples INNER JOIN Aposta ON idAposta=Aposta_idAposta WHERE Odd_idOdd = ?";
        return getStrings(idOdd, listaUtilizadores, query);
    }

    public Set<String> getUtilizadoresOddM(int idOdd) throws SQLException{
        Set<String> listaUtilizadores = new HashSet<>();
        String query = "SELECT Utilizador_email FROM Aposta_tem_Odd INNER JOIN Aposta ON idAposta=Multipla_Aposta_idAposta WHERE Odd_idOdd = ?";
        return getStrings(idOdd, listaUtilizadores, query);
    }

    private Set<String> getStrings(int idOdd, Set<String> listaUtilizadores, String query) throws SQLException {
        PreparedStatement ps  = c.prepareStatement(query);
        ps.setInt(1, idOdd);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String email = rs.getString("Utilizador_email");
            listaUtilizadores.add(email);
        }
        return listaUtilizadores;
    }

    public List<String> getUtilizadoresFav(String favorito, Desporto desporto) throws SQLException {
        List<String> listaUtilizadores = new ArrayList<>();
        String query = "SELECT Utilizador_email FROM Favorito WHERE Participante_idParticipante=? AND Participante_Desporto_idDesporto=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, favorito);
        ps.setInt(2, desporto.getIdDesporto());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String userId = rs.getString("Utilizador_email");
            listaUtilizadores.add(userId);
        }
        return listaUtilizadores;
    }

    public void addJogoASeguir(String idJogo, int idDesporto, String idUser) throws SQLException{
        String query = "INSERT INTO Jogo_a_Seguir(Jogo_idJogo,Jogo_Desporto_idDesporto,Apostador_Utilizador_email) VALUES(?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idJogo);
        ps.setInt(2, idDesporto);
        ps.setString(3, idUser);
        ps.executeQuery();
    }

    public void removeJogoASeguir (String idJogo, int idDesporto, String idUser) throws SQLException{
        String query = "DELETE FROM Jogo_a_Seguir WHERE Jogo_idJogo=? AND Jogo_Desporto_idDesporto=? AND Apostador_Utilizador_email=?";
        PreparedStatement ps  = c.prepareStatement(query);
        ps.setString(1,idJogo);
        ps.setInt(2, idDesporto);
        ps.setString(3, idUser);
        ps.executeQuery();
    }

    public Map<Integer, List<String>> getJogosASeguir (String idUser) throws SQLException{
        Map<Integer, List<String>> mapa = new HashMap<>();
        String query = "SELECT * FROM Jogo_a_Seguir WHERE Apostador_Utilizador_email=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idUser);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idDesporto = rs.getInt("Jogo_Desporto_idDesporto");
            String idJogo = rs.getString("Jogo_idJogo");
            mapa.putIfAbsent(idDesporto, new ArrayList<>());
            mapa.get(idDesporto).add(idJogo);
        }
        return mapa;
    }

    public void createSessao(Sessao s) throws SQLException{
        String query = "INSERT INTO Sessao(Utilizador_email, idSessao) VALUES(?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, s.getIdUser());
        ps.setString(2, s.getIdSession());
        ps.execute();
    }

    public void updateSessao(Sessao s) throws SQLException{
        String query = "UPDATE Sessao SET idSessao = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, s.getIdSession());
        ps.setString(2, s.getIdUser());
        ps.executeUpdate();
    }

    public String getUtilizadorSessao(String idSessao) throws SQLException{
        String user = "";
        String query = "Select Utilizador_email FROM Sessao WHERE idSessao=?;";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idSessao);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            user = rs.getString("Utilizador_email");
        }
        return user;
    }
}