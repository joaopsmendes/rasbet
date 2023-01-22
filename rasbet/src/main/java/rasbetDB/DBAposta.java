package rasbetDB;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Multipla;
import rasbetLN.GestaoApostas.Odd;
import rasbetLN.GestaoApostas.Simples;
import rasbetLN.GestaoJogos.Jogo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBAposta {
    private Connection c;

    public DBAposta(Connection connection){
        this.c = connection;
    }

    public void createSimples(String idUtilizador, float montante,float ganhos,int idOdd) throws SQLException{
        int idAposta = createAposta(idUtilizador,montante, ganhos);

        String query2 = "INSERT INTO Simples(Aposta_idAposta, Odd_idOdd)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setInt(1, idAposta);
        pstmt2.setInt(2, idOdd);
        pstmt2.execute();
    }

    public void createMultipla(String idUtilizador,float montante,float ganhos,List<Integer> listaOdd) throws SQLException{
        int idAposta = createAposta(idUtilizador,montante,ganhos);

        String query2 = "INSERT INTO Multipla(Aposta_idAposta)VALUES(?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setInt(1, idAposta);
        pstmt2.execute();

        for (int idOdd : listaOdd){
            insereOdd(idOdd,idAposta);
        }

    }

    public int createAposta(String idUtilizador,float montante,float ganhos) throws SQLException {
        String query1 = "INSERT INTO Aposta(Utilizador_email,montante,data, ganhoPossivel)VALUES(?,?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
        pstmt1.setString(1, idUtilizador);
        pstmt1.setFloat(2, montante);
        pstmt1.setString(3, LocalDateTime.now().toString());
        pstmt1.setFloat(4, ganhos);
        pstmt1.execute();

        ResultSet rs = pstmt1.getGeneratedKeys();
        int idAposta =0;
        if (rs.next()) idAposta= rs.getInt(1);
        return idAposta;

    }

    public void insereOdd(int idOdd,int idAposta) throws SQLException{
        String query = "INSERT INTO Aposta_tem_Odd(Multipla_Aposta_idAposta, Odd_idOdd)VALUES(?,?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, idAposta);
        pstmt.setInt(2, idOdd);
        pstmt.execute();
    }



    public Aposta getAposta(int idAposta, String email) throws SQLException{
        String query ="SELECT * FROM Aposta WHERE idAposta = ? AND Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ps.setString(2, email);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            float montante = rs.getFloat("montante");
            LocalDate dataAposta = rs.getDate("data").toLocalDate();
            Boolean resultado = rs.getBoolean("resultado");
            float ganhoPossivel = rs.getFloat("ganhoPossivel");
            if (rs.wasNull()){
                resultado = null;
            }
            query ="SELECT * FROM Simples WHERE Aposta_idAposta = ?";
            ps = c.prepareStatement(query);
            ps.setInt(1, idAposta);
            rs = ps.executeQuery();
            if(rs.next()){
                int idOdd = rs.getInt("Odd_idOdd");
                query ="SELECT opcao,valor,tema,titulo FROM Odd INNER JOIN ApostaJogo ON ApostaJogo_idApostaJogo=idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idOdd = ?";
                ps = c.prepareStatement(query);
                ps.setInt(1, idOdd);
                rs = ps.executeQuery();
                rs.next();
                String opcao = rs.getString("opcao");
                float valor = rs.getFloat("valor");
                String tema = rs.getString("tema");
                String titulo = rs.getString("titulo");
                String s = titulo + " - " + tema;
                return new Simples(idAposta, montante, dataAposta, resultado, ganhoPossivel,new Odd(idOdd, valor, opcao, s));
            }
            else{
                List<Odd> listaOdd = getListaOdd(idAposta);
                return new Multipla(idAposta, montante, dataAposta, resultado, ganhoPossivel, listaOdd);
            }
        }
        throw new SQLException("Can't get aposta");
    }
    public List<Odd> getListaOdd(int idAposta) throws SQLException{
        List<Odd> listaOdd = new ArrayList<>();
        String query ="SELECT idAposta,idOdd, opcao, valor, tema,titulo, idJogo FROM Aposta_tem_Odd INNER JOIN Aposta\n" +
                " ON idAposta=Multipla_Aposta_idAposta INNER JOIN Odd ON Odd_idOdd=idOdd INNER JOIN ApostaJogo ON \n" +
                "ApostaJogo_idApostaJogo=idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idOdd = rs.getInt("idOdd");
            String opcao = rs.getString("opcao");
            float valor = rs.getFloat("valor");
            String tema = rs.getString("tema");
            String titulo = rs.getString("titulo");
            String s = titulo + " - " + tema;
            listaOdd.add(new Odd(idOdd, valor, opcao, s));
        }
        return listaOdd;
    }


    public List<Aposta> getHistoricoApostas(String idUser) throws SQLException{
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ?";
        return getApostas(idUser,query);
    }

    public List<Aposta> getApostas(String idUser,String query) throws SQLException {
        List<Aposta> listApostas = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idUser);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idAposta = rs.getInt("idAposta");
            Aposta a = getAposta(idAposta, idUser);
            listApostas.add(a);
        }
        return listApostas;
    }
    public List<Aposta> getApostasAtivas(String idUser) throws SQLException{
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ? AND resultado is NOT NULL";
        return getApostas(idUser,query);

    }

    public void fecharAposta(int idAposta, boolean resultado) throws SQLException {
        String query = "UPDATE Aposta SET resultado = ? WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setBoolean(1,resultado);
        ps.setInt(2, idAposta);
        ps.execute();
    }

    public float getValorAposta(int id) throws SQLException {
        String query = "SELECT * FROM Simples WHERE Aposta_idAposta=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return getValorApostaSimples(id);
        }
        else {
            return getValorApostaMultipla(id);
        }
    }

    public float getValorApostaMultipla(int id) throws SQLException {
        String query = "SELECT valor,montante FROM Aposta INNER JOIN Aposta_tem_Odd ON idAposta=Multipla_Aposta_idAposta " +
                "INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        System.out.println(id);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float valor = 1, montante = 0;
        while (rs.next()){
            if (montante==0) montante=rs.getFloat("montante");
            System.out.println(valor);
            float num = rs.getFloat("valor");
            System.out.println(num);
            valor*=num;
        }
        return valor*montante;
    }
    public float getValorApostaSimples(int id) throws SQLException {
        String query = "SELECT valor,montante FROM Aposta INNER JOIN Simples ON idAposta=Aposta_idAposta " +
                "INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float valor = 0;
        if (rs.next()){
            valor = rs.getFloat("valor");
        }
        float montante=rs.getFloat("montante");
        return valor*montante;
    }

    private boolean isSimples (int idAposta) throws SQLException{
        String query = "SELECT * FROM Simples WHERE Aposta_idAposta=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void cashout(int idAposta) throws SQLException {
        if (isSimples(idAposta)){
            String query = "SELECT Aposta_idAposta,Estado_idEstado FROM Simples INNER JOIN Odd ON Odd_idOdd=idOdd " +
                    "INNER JOIN ApostaJogo ON ApostaJogo_idApostaJogo=idApostaJogo " +
                    "INNER JOIN Jogo ON Jogo_IdJogo=idJogo " +
                    "WHERE Aposta_idAposta= ? AND  Estado_idEstado=?";
            haJogosAtivos(idAposta, query);
        }
        else{
            String query = "SELECT Multipla_Aposta_idAposta,Estado_idEstado FROM Aposta_tem_Odd INNER JOIN Odd ON Odd_idOdd=idOdd " +
                    "INNER JOIN ApostaJogo ON ApostaJogo_idApostaJogo=idApostaJogo " +
                    "INNER JOIN Jogo ON Jogo_IdJogo=idJogo " +
                    "WHERE Aposta_idAposta= ? AND  Estado_idEstado=?";
            haJogosAtivos(idAposta, query);
        }
        String query = "UPDATE Aposta SET resultado=0 WHERE idAposta=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,idAposta);
        ps.executeUpdate();
    }

    private void haJogosAtivos(int idAposta, String query) throws SQLException {
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ps.setInt(2, Jogo.Estado.FECHADO.value);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            throw new SQLException("Existem Jogos a decorrer");
        }
    }

    public float getMontante(int idAposta) throws SQLException {
        String query = "SELECT montante FROM Aposta WHERE idAposta= ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float montante = 0;
        if (rs.next()){
            montante = rs.getFloat("montante");
        }
        return montante;
    }

    public Map<String, List<Float>> updateResulados(Map<Integer, List<Integer>> res) throws SQLException {
        updatePerdedores(res.get(0));
        return updateVencedores(res.get(1));
    }


    private void updatePerdedores(List<Integer> list) throws SQLException{
        for (int id : list) {
            String query = "UPDATE Aposta SET resultado=0 WHERE idAposta in (SELECT Aposta_idAposta FROM Simples WHERE odd_idOdd=?)";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();

            query = "UPDATE Aposta SET resultado=0 WHERE idAposta IN" +
                    "(SELECT Multipla_Aposta_idAposta FROM Aposta_tem_Odd INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idOdd = ?)";
            ps = c.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    private Map<String,List<Float>> updateVencedores(List<Integer> list) throws SQLException {
        Set<Integer> vencedores = new HashSet<>();
        Set<Integer> losers = new HashSet<>();
        Map<String, List<Float>> mapSaldos = new HashMap<>();
        for (int id : list) {
            String query = "UPDATE Aposta SET resultado=1 WHERE idAposta in (SELECT Aposta_idAposta FROM Simples WHERE odd_idOdd=?)";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

            query = "SELECT Utilizador_email,ganhoPossivel FROM Aposta  WHERE idAposta in (SELECT Aposta_idAposta FROM Simples WHERE odd_idOdd=?)";
            ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("Utilizador_email");
                float ganho = rs.getFloat("ganhoPossivel");
                mapSaldos.putIfAbsent(userId, new ArrayList<>());
                mapSaldos.get(userId).add(ganho);
            }

            query = "SELECT Multipla_Aposta_idAposta,Resultado FROM Aposta_tem_Odd INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idOdd = ?";
            ps = c.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int resultado = rs.getInt("resultado");
                System.out.println(resultado);
                int idAposta = rs.getInt("Multipla_Aposta_idAposta");
                if (resultado == 1) {
                    if (!losers.contains(idAposta))
                        vencedores.add(idAposta);
                } else {
                    if (!vencedores.contains(idAposta))
                        vencedores.remove(idAposta);
                    losers.add(idAposta);
                }
            }
            for (int idAposta : vencedores) {
                query = "UPDATE Aposta set resultado=1 WHERE idAposta= ?";
                ps = c.prepareStatement(query);
                ps.setInt(1, idAposta);
                ps.executeUpdate();

                query = "SELECT Utilizador_email,ganhoPossivel  FROM Aposta WHERE idAposta= ?";
                ps = c.prepareStatement(query);
                ps.setInt(1, idAposta);
                ps.executeQuery();
                if (rs.next()) {
                    String userId = rs.getString("Utilizador_email");
                    float ganho = rs.getFloat("ganhoPossivel");
                    mapSaldos.putIfAbsent(userId, new ArrayList<>());
                    mapSaldos.get(userId).add(ganho);
                }
            }
        }
        return mapSaldos;
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

    public Set<String> getUtilizadoresOdd(int idOdd) throws SQLException {
        Set<String> listaUtilizadores = getUtilizadoresOddSimples(idOdd);
        listaUtilizadores.addAll(getUtilizadoresOddMultipla(idOdd));
        return listaUtilizadores;
    }

    private Set<String> getUtilizadoresOddSimples(int idOdd) throws SQLException{
        Set<String> listaUtilizadores = new HashSet<>();
        String query = "SELECT Utilizador_email FROM Simples INNER JOIN Aposta ON idAposta=Aposta_idAposta WHERE Odd_idOdd = ?";
        return getStrings(idOdd, listaUtilizadores, query);
    }

    private Set<String> getUtilizadoresOddMultipla(int idOdd) throws SQLException{
        Set<String> listaUtilizadores = new HashSet<>();
        String query = "SELECT Utilizador_email FROM Aposta_tem_Odd INNER JOIN Aposta ON idAposta=Multipla_Aposta_idAposta WHERE Odd_idOdd = ?";
        return getStrings(idOdd, listaUtilizadores, query);
    }
}
