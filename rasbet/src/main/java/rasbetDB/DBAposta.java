package rasbetDB;

import rasbetLN.*;

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

    public Aposta createSimples(Aposta aposta, String idUtilizador, String idOdd) throws SQLException{
        createAposta(aposta, idUtilizador,0);

        String query2 = "INSERT INTO TipoAposta(TipoAposta, idTipoAposta)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, "simples");
        pstmt2.setInt(2, 0);
        pstmt2.execute();

        insereOdd(idOdd, aposta.getIdAposta());

        return new Simples(aposta.getIdAposta(),  aposta.getMontante(),  aposta.getDataAposta(),  idUtilizador, aposta.getResultado());
    }

    public Aposta createMultipla(Aposta aposta, String idUtilizador, List<String> listaOdd) throws SQLException{
        createAposta(aposta, idUtilizador, 1);

        String query2 = "INSERT INTO TipoAposta(TipoAposta, idTipoAposta)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, "multipla");
        pstmt2.setInt(2, 1);
        pstmt2.execute();

        for (String st : listaOdd){
            insereOdd(st, aposta.getIdAposta());
        }

        return new Multipla(aposta.getIdAposta(),  aposta.getMontante(),  aposta.getDataAposta(),  idUtilizador, aposta.getResultado());
    }

    public void insereOdd(String idOdd,int idAposta) throws SQLException{
        String query = "INSERT INTO Aposta_tem_Odd(Aposta_idAposta, Odd_idOdd)VALUES(?,?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, idAposta);
        pstmt.setString(2, idOdd);
        pstmt.execute();
    }

    public void createAposta(Aposta aposta, String idUtilizador, int tipo) throws SQLException {
        String query1 = "INSERT INTO Aposta(idAposta, montante, date, Utilizador_email, resultado, TipoAposta_idTipoAposta)VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1);
        pstmt1.setInt(1, aposta.getIdAposta());
        pstmt1.setFloat(2, aposta.getMontante());
        pstmt1.setDate(3, Date.valueOf(aposta.getDataAposta()));
        pstmt1.setString(4, idUtilizador);
        pstmt1.setBoolean(5, aposta.getResultado());
        pstmt1.setInt(6,tipo);
        pstmt1.execute();
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
