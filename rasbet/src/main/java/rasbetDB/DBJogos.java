package rasbetDB;

import rasbetLN.GestaoApostas.Odd;
import rasbetLN.GestaoJogos.ApostaJogo;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.Jogo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBJogos {

    private Connection c;

    public DBJogos(Connection connection){
        this.c = connection;
    }


    public void adicionarJogo(Jogo jogo) throws SQLException {
        String query = "INSERT INTO Jogo(idJogo,Desporto_idDesporto,Estado_idEstado,titulo,data)VALUES(?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,jogo.getIdJogo());
        ps.setInt(2,jogo.getDesporto());
        ps.setInt(3,jogo.getEstadoValue());
        ps.setString(4,jogo.getTitulo());
        ps.setString(5,jogo.getData().toString());
        ps.execute();

        adicionarParticipantes(jogo.getParticipantes(),jogo.getIdJogo(),jogo.getDesporto());

        for (ApostaJogo apostaJogo : jogo.getApostas().values()){
            adicionarApostaJogo(apostaJogo,jogo.getIdJogo(),jogo.getDesporto());
        }
    }

    public void adicionarParticipantes(List<String> list,String idJogo, int desporto) throws SQLException{
        String query = "INSERT IGNORE INTO Participante(idParticipante,Desporto_idDesporto) VALUES(?,?)";
        String query2 = "INSERT INTO Jogo_has_Participante(Participante_idParticipante,Desporto_idDesporto,Jogo_idJogo) VALUES(?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        PreparedStatement ps2 = c.prepareStatement(query2);

        for (String participante: list){
            ps.setString(1,participante);
            ps.setInt(2,desporto);
            ps.execute();
            ps2.setString(1,participante);
            ps2.setInt(2,desporto);
            ps2.setString(3,idJogo);
            ps2.execute();
        }
    }

    public List<String> getParticipantes(String idJogo) throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT Participante_idParticipante FROM Jogo_has_Participante WHERE Jogo_idJogo=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,idJogo);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String id = rs.getString("Participante_idParticipante");
            list.add(id);
        }
        return list;
    }

    public void adicionarApostaJogo(ApostaJogo apostaJogo,String idJogo,int idDesporto) throws SQLException {
        String query = "INSERT INTO ApostaJogo(tema,Jogo_idJogo,Jogo_Desporto_idDesporto)VALUES(?,?,?)";
        PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,apostaJogo.getTema());
        ps.setString(2,idJogo);
        ps.setInt(3,idDesporto);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        int idApostaJogo =0;
        if (rs.next()) idApostaJogo = rs.getInt(1);

        for (Odd odd : apostaJogo.getListOdd()){
            adicionarOdd(odd,idApostaJogo);
        }
    }

    public void adicionarOdd(Odd odd,int idApostaJogo) throws SQLException {
        String query = "INSERT INTO Odd(opcao,valor,ApostaJogo_idApostaJogo)VALUES(?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,odd.getOpcao());
        ps.setFloat(2,odd.getValor());
        ps.setInt(3,idApostaJogo);
        ps.execute();
    }

    public Map<String, Desporto> getDesportos() throws SQLException {
        Map<String,Desporto> mapDesportos = new HashMap<>();
        String query = "SELECT * FROM Desporto";
        PreparedStatement ps = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nomeDesporto = rs.getString("modalidade");
            int idDesporto = rs.getInt("idDesporto");
            System.out.println(nomeDesporto);
            Desporto desporto = new Desporto(idDesporto,nomeDesporto);
            mapDesportos.put(nomeDesporto,desporto);
        }
        return mapDesportos;
    }

    public Map<String,Jogo> getJogos(Desporto desporto) throws SQLException{
        Map<String,Jogo> map = new HashMap<>();
        //String query = "SELECT * FROM Jogo where Desporto_idDesporto = ? AND estado = 0";
        String query2 ="SELECT * FROM ApostaJogo " +
                "INNER JOIN Jogo ON Jogo.idJogo=ApostaJogo.Jogo_idJogo AND Jogo.Desporto_idDesporto=ApostaJogo.Jogo_Desporto_idDesporto " +
                "INNER JOIN Odd ON Odd.ApostaJogo_idApostaJogo=ApostaJogo.idApostaJogo WHERE Jogo.Desporto_idDesporto = ? and Jogo.Estado_idEstado = ?";
        PreparedStatement ps = c.prepareStatement(query2);
        ps.setInt(1, desporto.getIdDesporto());
        ps.setInt(2,Jogo.Estado.ATIVO.value);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String idJogo = rs.getString("idJogo");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime data = LocalDateTime.parse(rs.getString("data"),formatter);
            int idOdd = rs.getInt("idOdd");
            float valorOdd = rs.getFloat("valor");
            String tema = rs.getString("tema");
            String opcao = rs.getString("opcao");
            String titulo = rs.getString("titulo");


            map.putIfAbsent(idJogo,new Jogo(idJogo, desporto, data,titulo, Jogo.Estado.ATIVO,getParticipantes(idJogo)));
            Jogo jogo = map.get(idJogo);

            Odd odd = new Odd(idOdd,valorOdd, opcao, jogo.getIdJogo());
            jogo.addOdd(tema,odd);

        }
        return map;
    }
    
    
    public Map<String,ApostaJogo> apostasJogo(Jogo jogo) throws SQLException{
        Map<String,ApostaJogo> apostas = new HashMap<>();
        String query = "SELECT * FROM ApostaJogo where Jogo_idJogo = ? AND Jogo_Desporto_idDesporto = ? AND resultado IS NULL";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, jogo.getIdJogo());
        ps.setInt(2, jogo.getDesporto());

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String tema = rs.getString("tema");
            int idApostaJogo = rs.getInt("idApostaJogo");


            System.out.println("Aposta Jogo " + idApostaJogo);


            ApostaJogo apostaJogo = new ApostaJogo(tema);

            List<Odd> listOdd = oddApostaJogo(tema,jogo);
            apostaJogo.setListOdd(listOdd);

            apostas.put(tema,apostaJogo);
        }

        return apostas;
    }


    public List<Odd> oddApostaJogo(String tema, Jogo jogo) throws SQLException{
        List<Odd> list = new ArrayList<>();
        String query = "SELECT * FROM Odd Where ApostaJogo_tema = ? AND ApostaJogo_Jogo_idJogo = ? AND ApostaJogo_Jogo_Desporto_idDesporto = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, tema);
        ps.setString(2, jogo.getIdJogo());
        ps.setInt(3, jogo.getDesporto());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idOdd = rs.getInt("idOdd");
            float valor = rs.getFloat("valor");
            String opcao = rs.getString("opcao");
            Odd odd = new Odd(idOdd,valor, opcao, jogo.getIdJogo());
            list.add(odd);
        }
        return list;
    }


    public void alteraEstado(String idJogo, int estado) throws SQLException {
        String query = "UPDATE Jogo SET Estado_idEstado = ? WHERE idJogo = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, estado);
        ps.setString(2, idJogo);
        ps.execute();
        System.out.println("UPDATED");
    }

    public boolean existeJogo(String gameId, Desporto desporto) throws SQLException {
        String query = "SELECT * FROM Jogo WHERE idJogo = ? AND Desporto_idDesporto = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,gameId);
        ps.setInt(2, desporto.getIdDesporto());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public int updateVencedor(String idJogo, String vencedor) throws SQLException {
        String query ="SELECT idOdd FROM Jogo " +
                "INNER JOIN ApostaJogo ON idJogo=Jogo_idJogo " +
                "INNER JOIN Odd ON idApostaJogo=ApostaJogo_idApostaJogo " +
                "WHERE idJogo = ? AND Resultado is NULL AND opcao=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,idJogo);
        ps.setString(2,vencedor);
        ResultSet rs = ps.executeQuery();
        int win = 0;
        if(rs.next()){
            int idOdd = rs.getInt("idOdd");
            win = idOdd;
            query="UPDATE Odd SET Resultado=1 WHERE idOdd =?";
            ps = c.prepareStatement(query);
            ps.setInt(1,idOdd);
            ps.execute();
        }
        return win;
    }
    public List<Integer> updatePerdedores(String idJogo, String vencedor) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String query ="SELECT idOdd FROM Jogo " +
                "INNER JOIN ApostaJogo ON idJogo=Jogo_idJogo " +
                "INNER JOIN Odd ON idApostaJogo=ApostaJogo_idApostaJogo " +
                "WHERE idJogo = ? AND Resultado is NULL AND opcao!=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,idJogo);
        ps.setString(2,vencedor);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idOdd = rs.getInt("idOdd");
            list.add(idOdd);
            query="UPDATE Odd SET Resultado=0 WHERE idOdd =?";
            ps = c.prepareStatement(query);
            ps.setInt(1,idOdd);
            ps.execute();
        }
        return list;
    }

    public Map<Integer,List<Integer>> updateResultado(String idJogo, String vencedor) throws SQLException {
        Map<Integer,List<Integer>> map = new HashMap<>();
        int winner = updateVencedor(idJogo,vencedor);
        List<Integer> winnerList = new ArrayList<>();
        winnerList.add(winner);
        map.put(1,winnerList);
        List<Integer> losers = updatePerdedores(idJogo, vencedor);
        map.put(0,losers);
        alteraEstado(idJogo, Jogo.Estado.FECHADO.value);
        return map;
    }

    public void updateOdd(int idOdd, float valor) throws SQLException{
        String query = "UPDATE Odd SET valor=? WHERE idOdd =?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, valor);
        ps.setInt(2, idOdd);
        ps.execute();
    }

    public void updateEstadoJogos() throws SQLException {
        String query = "UPDATE Jogo SET Estado_idEstado=? WHERE data <= ?";
        LocalDateTime now = LocalDateTime.now();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, Jogo.Estado.FECHADO.value);
        ps.setString(2,now.toString());
        ps.executeUpdate();
    }

    public List<Integer> getOddsJogo(int idOdd) throws SQLException{
        List<Integer> listaOdds = new ArrayList<>();
        String query1 = "SELECT idJogo FROM Odd INNER JOIN ApostaJogo ON idApostaJogo=ApostaJogo_idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idOdd = ?";
        PreparedStatement ps = c.prepareStatement(query1);
        ps.setInt(1, idOdd);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String idJogo = rs.getString("idJogo");

            String query2 = "SELECT idOdd FROM Odd INNER JOIN ApostaJogo ON idApostaJogo=ApostaJogo_idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idJogo = ?";
            PreparedStatement pst = c.prepareStatement(query1);
            ps.setString(1, idJogo);
            ResultSet rst = ps.executeQuery();
            while (rst.next()){
                int Odd = rst.getInt("idOdd");
                listaOdds.add(Odd);
            }
        }
        return listaOdds;
    }

    public String getTituloJogo(int idOdd) throws SQLException{
        String titulo="";
        String query = "SELECT titulo FROM Odd INNER JOIN ApostaJogo ON idApostaJogo=ApostaJogo_idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idOdd=?;";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idOdd);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            titulo = rs.getString("titulo");
        }
        return titulo;
    }

//    public Desporto getDesporto(String idJogo) throws SQLException {
//        String query = "SELECT * FROM Jogo INNER JOIN Desporto ON idDesporto = Desporto_idDesporto WHERE idJogo = ?";
//        PreparedStatement ps = c.prepareStatement(query);
//        ps.setString(1,idJogo);
//        ResultSet rs = ps.executeQuery();
//        if(rs.next()){
//            int idDesporto = rs.getInt("idDesporto");
//            String modalidade = rs.getString("modalidade");
//            Desporto desporto = new Desporto(idDesporto,modalidade);
//            return desporto;
//        }
//        return null;
//    }
}
