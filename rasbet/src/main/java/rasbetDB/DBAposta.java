package rasbetDB;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Multipla;
import rasbetLN.GestaoApostas.Simples;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBAposta {
    private Connection c;

    public DBAposta(Connection connection){
        this.c = connection;
    }

    public void createSimples(String idUtilizador, float montante,int idOdd) throws SQLException{
        int idAposta = createAposta(idUtilizador,montante);

        String query2 = "INSERT INTO Simples(Aposta_idAposta, Odd_idOdd)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setInt(1, idAposta);
        pstmt2.setInt(2, idOdd);
        pstmt2.execute();
    }

    public void createMultipla(String idUtilizador,float montante,List<Integer> listaOdd) throws SQLException{
        int idAposta = createAposta(idUtilizador,montante);

        String query2 = "INSERT INTO Multipla(Aposta_idAposta)VALUES(?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setInt(1, idAposta);
        pstmt2.execute();

        for (int idOdd : listaOdd){
            insereOdd(idOdd,idAposta);
        }

    }

    public int createAposta(String idUtilizador,float montante) throws SQLException {
        String query1 = "INSERT INTO Aposta(Utilizador_email,montante,data)VALUES(?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
        pstmt1.setString(1, idUtilizador);
        pstmt1.setFloat(2, montante);
        pstmt1.setString(3, LocalDateTime.now().toString());
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
            boolean resultado = rs.getBoolean("resultado");
            int tipo = rs.getInt("TipoAposta_idTipoAposta");
            if(tipo == 0){
                return new Simples(idAposta, montante, dataAposta, email, resultado);
            }else if(tipo == 1){
                return new Multipla(idAposta, montante, dataAposta, email, resultado);
            }else throw new SQLException("Aposta não existe");
        }
        throw new SQLException("Can't get aposta");
    }

    public List<Aposta> getHistoricoApostas(String idUser) throws SQLException{
        List<Aposta> historicoApostas = new ArrayList<>();
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ? AND resultado is NULL";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idUser);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idAposta = rs.getInt("idAposta");
             Aposta a = getAposta(idAposta, idUser);
             historicoApostas.add(a);
        }
        return historicoApostas;
    }

    public List<Aposta> getApostasAtivas(String idUser) throws SQLException{
        List<Aposta> apostasAtivas = new ArrayList<>();
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ? AND resultado is NOT NULL";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idUser);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idAposta = rs.getInt("idAposta");
            Aposta a = getAposta(idAposta, idUser);
            apostasAtivas.add(a);
        }
        return apostasAtivas;
    }
}
