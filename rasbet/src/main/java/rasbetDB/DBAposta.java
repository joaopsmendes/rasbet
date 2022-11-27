package rasbetDB;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Multipla;
import rasbetLN.GestaoApostas.Odd;
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
            List<Odd> listaOdd = getListaOdd(idAposta);
            query ="SELECT * FROM Simples WHERE Aposta_idAposta = ?";
            ps = c.prepareStatement(query);
            ps.setInt(1, idAposta);
            rs = ps.executeQuery();
            if(rs.next()){
                return new Simples(idAposta, montante, dataAposta, resultado,listaOdd);
            }
            else{
                return new Multipla(idAposta, montante, dataAposta, resultado,listaOdd);
            }
        }
        throw new SQLException("Can't get aposta");
    }
    public List<Odd> getListaOdd(int idAposta) throws SQLException{
        List<Odd> listaOdd = new ArrayList<>();
        String query ="SELECT idAposta,idOdd, opcao, valor, tema,titulo, idJogo FROM Aposta_tem_Odd INNER JOIN Apost\n" +
                "a ON idAposta=Multipla_Aposta_idAposta INNER JOIN Odd ON Odd_idOdd=idOdd INNER JOIN ApostaJogo ON Apost\n" +
                "aJogo_idApostaJogo=idApostaJogo INNER JOIN Jogo ON idJogo=Jogo_idJogo WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idOdd = rs.getInt("Odd_idOdd");
            String opcao = rs.getString("opcao");
            float valor = rs.getFloat("valor");
            String tema = rs.getString("tema");
            String titulo = rs.getString("titulo");
            String idJogo = rs.getString("idJogo");

            listaOdd.add(new Odd(idOdd, valor, opcao, idJogo));
        }
        return listaOdd;
    }


    public List<Aposta> getHistoricoApostas(String idUser) throws SQLException{
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ? AND resultado is NULL";
        return getApostas(idUser,query);
    }

    public List<Aposta> getApostas(String idUser,String query) throws SQLException {
        List<Aposta> listApostas = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, idUser);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int idAposta = rs.getInt("idAposta");
            Aposta a = getAposta(idAposta, idUser);
            listApostas.add(a);
        }
        return listApostas;
    }
    public List<Aposta> getApostasAtivas(String idUser) throws SQLException{
        String query = "SELECT * FROM Aposta WHERE Utilizador_email = ? AND resultado is NOT NULL";
        return getApostas(idUser,query);

    }

    public void fecharAposta(int idAposta, boolean resultado) throws SQLException {
        String query = "UPDATE Aposta SET resultado = ? WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setBoolean(1,resultado);
        ps.setInt(2, idAposta);
        ps.execute();
    }

    public float getValorAposta(int id) throws SQLException {
        String query = "SELECT * FROM Simples WHERE Aposta_idAposta=?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return getValorApostaSimples(id);
        }
        else {
            return getValorApostaMultipla(id);
        }
    }

    public float getValorApostaMultipla(int id) throws SQLException {
        String query = "SELECT valor,montante FROM Aposta INNER JOIN Aposta_tem_Odd ON idAposta=Multipla_Aposta_idAposta " +
                "INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        System.out.println(id);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float valor = 1, montante = 0;
        while (rs.next()){
            if (montante==0) montante=rs.getFloat("montante");
            System.out.println(valor);
            float num = rs.getFloat("valor");
            System.out.println(num);
            valor*=num;
        }
        return valor*montante;
    }
    public float getValorApostaSimples(int id) throws SQLException {
        String query = "SELECT valor,montante FROM Aposta INNER JOIN Simples ON idAposta=Aposta_idAposta " +
                "INNER JOIN Odd ON Odd_idOdd=idOdd WHERE idAposta = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float valor = 0;
        if (rs.next()){
            valor = rs.getFloat("valor");
        }
        float montante=rs.getFloat("montante");
        return valor*montante;
    }

    public void cashout(int idAposta) {
        //TODO Think about this
        
    }
    
    public float getMontante(int idAposta) throws SQLException {
        String query = "SELECT montante FROM Aposta WHERE idAposta= ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, idAposta);
        ps.execute();
        ResultSet rs = ps.executeQuery();
        float montante = 0;
        if (rs.next()){
            montante = rs.getFloat("montante");
        }
        return montante;
    }
}
