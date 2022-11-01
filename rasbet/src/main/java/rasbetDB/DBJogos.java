package rasbetDB;

import rasbetLN.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBJogos {

    private Connection c;

    public DBJogos(Connection connection){
        this.c = connection;
    }


    public void adicionarJogo(Jogo jogo) {
        String query1 = "INSERT INTO Jogo(idJogo,Desporto_idDesporto,resultado)VALUES(?,?,?)";
        //PreparedStatement pstmt1 = c.prepareStatement(query1);
        //pstmt1.setString(1, jogo.getIdJogo());
        //pstmt1.setDate(2, Date.valueOf(dataNascimento));
        //pstmt1.setString(3, nif);
        //pstmt1.setString(4, password);
        //pstmt1.setString(5, nome);
        //pstmt1.execute();
    }

    public Map<Integer,Desporto> getDesportos() throws SQLException {
        Map<Integer,Desporto> mapDesportos = new HashMap<>();
        String query = "SELECT * FROM Desporto";
        PreparedStatement ps = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nomeDesporto = rs.getString("modalidade");
            int idDesporto = rs.getInt("idDesporto");
            System.out.println(nomeDesporto);
            Desporto desporto = new Desporto(idDesporto,nomeDesporto);
            mapDesportos.put(idDesporto,desporto);
        }
        return mapDesportos;
    }
}
