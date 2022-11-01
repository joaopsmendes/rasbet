package rasbetDB;

import rasbetLN.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DBAposta {
    private Connection c;

    public DBAposta(Connection connection){
        this.c = connection;
    }

    public Aposta createSimples(Aposta aposta, String idUtilizador) throws SQLException{
        createAposta(aposta, idUtilizador,0);

        String query2 = "INSERT INTO TipoAposta(TipoAposta, idTipoAposta)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, "simples");
        pstmt2.setInt(2, 0);
        pstmt2.execute();

        return new Simples(aposta.getIdAposta(),  aposta.getMontante(),  aposta.getDataAposta(),  idUtilizador, aposta.getResultado());
    }

    public Aposta createMultipla(Aposta aposta, String idUtilizador) throws SQLException{
        createAposta(aposta, idUtilizador, 1);

        String query2 = "INSERT INTO TipoAposta(TipoAposta, idTipoAposta)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, "multipla");
        pstmt2.setInt(2, 1);
        pstmt2.execute();

        return new Multipla(aposta.getIdAposta(),  aposta.getMontante(),  aposta.getDataAposta(),  idUtilizador, aposta.getResultado());
    }

    private void createAposta(Aposta aposta, String idUtilizador, int tipo) throws SQLException {
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

    public Aposta getAposta(String email){
        return null;
    }






}
