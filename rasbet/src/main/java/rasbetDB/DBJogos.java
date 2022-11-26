package rasbetDB;

import rasbetLN.GestaoApostas.Odd;
import rasbetLN.GestaoJogos.ApostaJogo;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.Jogo;

import java.sql.*;
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
        String query = "INSERT INTO Jogo(idJogo,Desporto_idDesporto,Estado_idEstado,data)VALUES(?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,jogo.getIdJogo());
        ps.setInt(2,jogo.getDesporto());
        ps.setInt(3,jogo.getEstadoValue());
        ps.setString(4,jogo.getData().toString());
        ps.execute();

        for (ApostaJogo apostaJogo : jogo.getApostas().values()){
            adicionarApostaJogo(apostaJogo,jogo.getIdJogo(),jogo.getDesporto());
        }
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

        for (Odd odd : apostaJogo.getMapOdd().values()){
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
        String query2 ="SELECT Jogo.idJogo, Jogo.Data,Odd.idOdd,ApostaJogo.idApostaJogo,ApostaJogo.tema,Odd.valor,Jogo.Desporto_idDesporto,Odd.opcao FROM ApostaJogo " +
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


            map.putIfAbsent(idJogo,new Jogo(idJogo, desporto, data, Jogo.Estado.ATIVO));
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

            Map<String,Odd> mapOdd = oddApostaJogo(tema,jogo);
            apostaJogo.setMapOdd(mapOdd);

            apostas.put(tema,apostaJogo);
        }

        return apostas;
    }


    public Map<String,Odd> oddApostaJogo(String tema, Jogo jogo) throws SQLException{
        Map<String,Odd> map = new HashMap<>();
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
            map.put(odd.getOpcao(),odd);
        }
        return map;
    }


    public void alteraEstado(String idJogo, Jogo.Estado estado) throws SQLException {
        String query = "UPDATE Jogo SET Estado_idEstado = ? WHERE idJogo = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, estado.value);
        ps.setString(2, idJogo);
        ps.execute();
    }

    public boolean existeJogo(String gameId, Desporto desporto) throws SQLException {
        String query = "SELECT * FROM Jogo WHERE idJogo = ? AND Desporto_idDesporto = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,gameId);
        ps.setInt(2, desporto.getIdDesporto());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public void updateVencedor(String idJogo, String vencedor) throws SQLException {
        String query ="SELECT idOdd FROM Jogo " +
                "INNER JOIN ApostaJogo ON idJogo=Jogo_idJogo " +
                "INNER JOIN Odd ON idApostaJogo=ApostaJogo_idApostaJogo " +
                "WHERE idJogo = ? AND Resultado is NULL AND opcao=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,idJogo);
        ps.setString(2,vencedor);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int idOdd = rs.getInt("idOdd");
            query="UPDATE Odd SET Resultado=1 WHERE idOdd =?";
            ps = c.prepareStatement(query);
            ps.setInt(1,idOdd);
            ps.execute();
        }
    }
    public void updatePerdedores(String idJogo, String vencedor) throws SQLException {
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
            query="UPDATE Odd SET Resultado=0 WHERE idOdd =?";
            ps = c.prepareStatement(query);
            ps.setInt(1,idOdd);
            ps.execute();
        }
    }

    public void updateResultado(String idJogo, String vencedor) throws SQLException {
        updateVencedor(idJogo,vencedor);
        updatePerdedores(idJogo, vencedor);
    }

    public void updateOdd(int idOdd, float valor) throws SQLException{
        String query = "UPDATE Odd SET valor=? WHERE idOdd =?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, valor);
        ps.setInt(2, idOdd);
        ps.execute();
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
