package rasbetDB;

import rasbetLN.*;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DBUtilizadores {
    private Connection c;

    public DBUtilizadores(Connection connection) {
        this.c = connection;
    }

    public Utilizador createApostador(String email, LocalDate dataNascimento, String nif, String password, String tipo, String nome) throws SQLException {
        String query1 = "INSERT INTO Utilizador(email, dataNascimento, NIF, pass, nome)VALUES(?,?,?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1);
        pstmt1.setString(1, email);
        pstmt1.setDate(2, Date.valueOf(dataNascimento));
        pstmt1.setString(3, nif);
        pstmt1.setString(4, password);
        pstmt1.setString(5, nome);
        pstmt1.execute();

        String query2 = "INSERT INTO TipoUtilizador(Tipo, Utilizador_NIF)VALUES(?,?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, tipo);
        pstmt2.setString(2, nif);
        pstmt2.execute();

        return new Apostador(email, password, dataNascimento, nif, nome);
    }

    public Utilizador logIn(String email, String password) throws SQLException {
        String query = "SELECT Utilizador.email, Utilizador.pass FROM Utilizador WHERE email = ? AND password = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, password);
        int i = ps.executeUpdate();
        if (i == 0) {
            throw new SQLException("Utilizador não existe");
        } else {
            System.out.println("Login feito com sucesso");
            return getUtilizador(email);
        }
    }

    public Utilizador getUtilizador(String email) throws SQLException {
        String query = "SELECT Utilizador.nome, Utilizador.pass, Utilizador.dataNAscimento, Utilizador.nif, TipoUtilizador.tipo FROM Utilizador" +
                " INNER JOIN TipoUtilizador ON Utilizador.email = TipoUtilizador.Utilizador_email" +
                " WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nome = rs.getString("nome");
            String password = rs.getString("pass");
            LocalDate dataNascimento = rs.getDate("dataNascimento").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String nif = rs.getString("nif");
            String tipo = rs.getString("tipo");
            if (tipo.equals("Apostador")) {
                return new Apostador(email, password, dataNascimento, nif, nome);
            } else if (tipo.equals("Administrador")) {
                return new Administrador(email, password, dataNascimento, nif, nome);
            } else if (tipo.equals("Especialista")) {
                return new Especialista(email, password, dataNascimento, nif, nome);
            } else throw new SQLException("Utilizador não existe");

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

    public void replaceNome(String email, String nome) throws SQLException {
        String query = "UPDATE utilizador SET Nome = ? WHERE Email = ?";
        PreparedStatement ps;
        ps = c.prepareStatement(query);
        ps.setString(1, nome);
        ps.setString(2, email);
        ps.execute();
    }

    public void replaceEmail(String email, String nif) throws SQLException {
        String query = "UPDATE utilizador SET Email = ? WHERE nif = ?";
        PreparedStatement ps;
        ps = c.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, nif);
        ps.execute();
    }
/*
    public void replaceTelemovel(String email, String nif) throws SQLException {

    }

    public void replaceMorada(String email, String nif) throws SQLException {

    }
 */

    public Utilizador logOut(String email) throws SQLException { //duvidas
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
        String query = "INSERT INTO Favorito(favorito, Utilizador_email, Desporto_idDesporto)VALUES (?,?,?) ";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, favorito.getNome());
        pstmt.setString(2, userId);
        pstmt.setInt(3, favorito.getDesporto().getIdDesporto());
        pstmt.execute();
    }

    public void removeFavorito(String userId, Favorito favorito) throws SQLException {
        String query = "DELETE FROM Favorito WHERE favorito = ? AND  Utilizador_emial=? AND Desporto_idDesporto = ?";
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

    public void updateSaldo(float saldo, String userId) throws SQLException {
        String query = "UPDATE Carteira SET saldo = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, saldo);
        ps.setString(2, userId);
        ps.execute();
    }

    public void novoDeposito(Deposito deposito, String userId) throws SQLException {
        String query = "INSERT INTO Transação(valor, dataTransacao, Carteira_Utilizador_Email)VALUES (?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, deposito.getValor());
        ps.setString(2, deposito.getData().toString());
        ps.setString(3, userId);
        ps.execute();
        ps.execute();

    }

    public void novoLevantamento(Levantamento levantamento, String userId) throws SQLException {
        String query = "INSERT INTO Transação(valor, dataTransacao, Carteira_Utilizador_Email)VALUES (?,?,?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, levantamento.getValor());
        ps.setObject(2, levantamento.getData());
        ps.setString(3, userId);
        ps.execute();
    }



}