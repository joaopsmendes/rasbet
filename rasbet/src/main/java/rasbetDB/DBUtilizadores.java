package rasbetDB;

import rasbetLN.GestaoUtilizadores.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DBUtilizadores {
    private Connection c;

    public DBUtilizadores(Connection connection) {
        this.c = connection;
    }

    public void createApostador(Apostador apostador) throws SQLException {

        createUtilizador(apostador);

        String query2 = "INSERT INTO Apostador(Utilizador_email)VALUES(?)";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, apostador.getEmail());
        pstmt2.execute();

        criarCarteira(apostador.getEmail());
    }

    public void createUtilizador(Utilizador utilizador) throws SQLException {
        String query1 = "INSERT INTO Utilizador(email, dataNascimento, NIF, pass, nome, telemovel, morada)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt1 = c.prepareStatement(query1);
        pstmt1.setString(1, utilizador.getEmail());
        pstmt1.setDate(2, Date.valueOf(utilizador.getDataNascimento()));
        pstmt1.setString(3, utilizador.getNIF());
        pstmt1.setString(4, utilizador.getPassword());
        pstmt1.setString(5, utilizador.getNome());
        pstmt1.setString(6, utilizador.getTelemovel());
        pstmt1.setString(7, utilizador.getMorada());
        pstmt1.execute();

    }

    public void criarCarteira(String userId) throws SQLException {
        String query1 = "INSERT INTO Carteira(Utilizador_email,saldo,freebets)VALUES(?,?,?)";
        PreparedStatement st = c.prepareStatement(query1);
        st.setString(1, userId);
        st.setFloat(2,0);
        st.setFloat(3,5);
        st.execute();

    }


    public void logIn(String email, String password) throws SQLException {
        String query = "SELECT email, pass FROM Utilizador WHERE email = ? AND pass = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Utilizador não existe");
        } else {
            System.out.println("Login feito com sucesso");
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
            LocalDate dataNascimento = LocalDate.parse(rs.getString("dataNascimento"));
            String nif = rs.getString("nif");
            String telemovel = rs.getString("telemovel");
            String morada = rs.getString("morada");
            String tipo = rs.getString("tipo");
            if (tipo.equals("Apostador")) {
                return new Apostador(email, password, dataNascimento, nif, nome, telemovel, morada);
            } else if (tipo.equals("Administrador")) {
                return new Administrador(email, password, dataNascimento, nif, nome, telemovel, morada);
            } else if (tipo.equals("Especialista")) {
                return new Especialista(email, password, dataNascimento, nif, nome, telemovel, morada);
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

//    public void replaceNome(String email, String nome) throws SQLException {
//        String query = "UPDATE utilizador SET Nome = ?WHERE Email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, nome);
//        ps.setString(2, email);
//        ps.execute();
//    }
//
//    public void replaceEmail(String email, String nif) throws SQLException {
//        String query = "UPDATE utilizador SET Email = ? WHERE nif = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, email);
//        ps.setString(2, nif);
//        ps.execute();
//    }
//
//
//    public void replaceTelemovel(String email, String telemovel) throws SQLException {
//        String query = "UPDATE utilizador SET telemovel = ? WHERE email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, telemovel);
//        ps.setString(2, email);
//        ps.execute();
//    }
//
//    public void replaceMorada(String email, String morada) throws SQLException {
//        String query = "UPDATE utilizador SET morada = ? WHERE email = ?";
//        PreparedStatement ps;
//        ps = c.prepareStatement(query);
//        ps.setString(1, morada);
//        ps.setString(2, email);
//        ps.execute();
//    }

    public void replaceUtilizador(Utilizador user, String oldEmail) throws SQLException{
        String query = "UPDATE utilizador SET nome = ?, pass = ?, telemovel = ?, morada = ?, email = ? WHERE email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, user.getNome());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getTelemovel());
        ps.setString(4, user.getMorada());
        ps.setString(5, user.getEmail());
        ps.setString(6, oldEmail);
        ps.execute();

    }

// Não sei se é preciso
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
        String query = "DELETE FROM Favorito WHERE favorito = ? AND  Utilizador_email=? AND Desporto_idDesporto = ?";
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

    public void updateFreebets(float freebets, String userId) throws SQLException {
        String query = "UPDATE Carteira SET freebets = ? WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setFloat(1, freebets);
        ps.setString(2, userId);
        ps.execute();
    }

    public int novaTransacao(Transacao transacao,String userId) throws SQLException {
        String query = "INSERT INTO Transacao(valor, dataTransacao, Utilizador_Email)VALUES (?,?,?)";
        PreparedStatement ps = c.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setFloat(1, transacao.getValor());
        ps.setString(2, transacao.getData().toString());
        ps.setString(3, userId);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        int value = 0;
        if(rs.next()) value = rs.getInt(1);
        return value;
    }

    public void novoDeposito(Deposito deposito, String userId) throws SQLException {
        int idTransacao = novaTransacao(deposito,userId);
        String query = "INSERT INTO Deposito(Transacao_idTransacao)VALUES (?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,idTransacao);
        ps.execute();
    }

    public void novoLevantamento(Levantamento levantamento, String userId) throws SQLException {
        int idTransacao = novaTransacao(levantamento,userId);
        String query = "INSERT INTO Levantamento(Transacao_idTransacao)VALUES (?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,idTransacao);
        ps.execute();

    }

    public Transacao getTransacao(int idT, String userId) throws SQLException{
        String query ="SELECT * FROM Transacao WHERE idTransacao = ? AND Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idT);
        ps.setString(2, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            float valor = rs.getFloat("valor");
            LocalDateTime dataTransacao = LocalDateTime.parse(rs.getString("dataTransacao"));
            int tipo = rs.getInt("TipoTransicao_idTipoTransacao");
            if(tipo == 0){
                //seja 0 - Levantamento
                return new Levantamento(valor, dataTransacao);
            }
            else new Deposito(valor, dataTransacao);
        }
        return null;
    }

    public List<Transacao> getHistTransacoes(String userId) throws SQLException{
        List<Transacao> transacoes = new ArrayList<>();
        String query = "SELECT * FROM Carteira WHERE Utilizador_email = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idtransacao = rs.getInt("idTransacao");
            //float valor = rs.getFloat("valor");
            //LocalDateTime dataTransacao = rs.getTimestamp("dataTransacao").toLocalDateTime();
            //int tipo = rs.getInt("TipoTransicao_idTipoTransacao");
            //if(tipo == 0){
                //seja 0 - Levantamento
            //    transacoes.add(new Levantamento(valor, dataTransacao));
            //}
            //else transacoes.add(new Deposito(valor, dataTransacao));
            Transacao a = getTransacao(idtransacao, userId);
            transacoes.add(a);
        }
        return transacoes;
    }

}