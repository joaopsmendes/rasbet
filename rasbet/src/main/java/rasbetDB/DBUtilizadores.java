package rasbetDB;

import rasbetLN.Apostador;
import rasbetLN.Utilizador;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBUtilizadores {
    private Connection c;

    public DBUtilizadores(Connection connection){
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
        pstmt1.execute();

        return new Apostador(email, password, dataNascimento.toString(), nif, nome);
    }

    public Utilizador logIn(String email, String password) throws SQLException{
        String query = "SELECT Utilizador.email, Utilizador.pass FROM Utilizador WHERE email = ? AND password = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,email);
        ps.setString(2,password);
        int i = ps.executeUpdate();
        if (i  == 0){
            throw new SQLException("Utilizador não existe");
        }
        else {
            System.out.println("Login feito com sucesso");
            return getUtilizador(email);
        }
    }

    public Utilizador getUtilizador(String email) throws SQLException{//duvidas no return
        String query = "SELECT Utilizador.nome, Utilizador.pass, Utilizador.dataNAscimento, Utilizador.nif, TipoUtilizador.tipo FROM Utilizador" +
                            " INNER JOIN TipoUtilizador ON Utilizador.email = TipoUtilizador.Utilizador_email" +
                            " WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nome = rs.getString("nome");
            String password = rs.getString("pass");
            String dataNascimento = rs.getString("dataNascimento");
            String nif = rs.getString("nif");
            String tipo = rs.getString("tipo");

//            return new Utilizador(email, password, dataNascimento, nif, nome);

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

//    public void replaceTelemovel(String email, String nif) throws SQLException {
//
//    }
//
//    public void replaceMorada(String email, String nif) throws SQLException {
//
//    }

    public Utilizador logOut(String email) throws SQLException{ //duvidas
        String query = "SELECT Utilizador.email FROM Utilizador WHERE email = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,email);
        int i = ps.executeUpdate();
        if (i == 0){
            System.out.println("Logout falhado");
        }
        else System.out.println("Logout efetuado");
        return null;
    }
}
