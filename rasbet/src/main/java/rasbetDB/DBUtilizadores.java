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

    public Utilizador logIn(String email, String password){ //refazer
//        String query = "UPDATE utilizador SET LoggedIn = ? WHERE email = ? AND password = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1,email);
//        ps.setString(2,password);
//        int i = ps.executeUpdate();
//        if (i  == 0){
//            throw new SQLException("Utilizador não existe");
//        }
//        else {
//            System.out.println("Login feito com sucesso");
//            return getUtilizador(email);
//        }
    }

    private Utilizador getUtilizador(String email) throws SQLException{//fazer
        String query1 = "SELECT * FROM TipoUtilizador where email = ?";
        //...

        String query = "SELECT * FROM utilizador WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String password = rs.getString("pass");
            String dataNascimento = rs.getString("dataNascimento");
            String nif = rs.getString("NIF");
            String nome = rs.getString("nome");
//            return new Utilizador(email, password, dataNascimento, nif, nome);

        }
        throw new SQLException("Can't get user");
    }


    public Utilizador logOut(String email){

    }
}
