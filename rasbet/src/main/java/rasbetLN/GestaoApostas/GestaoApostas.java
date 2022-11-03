package rasbetLN.GestaoApostas;

import rasbetDB.DBAposta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GestaoApostas implements IGestaoApostas {
    private DBAposta apostas;

    public GestaoApostas(Connection connection){
        this.apostas = new DBAposta(connection);
    }


    @Override
    public Aposta getAposta(int idAposta, String email) throws SQLException{
        return this.apostas.getAposta(idAposta, email);
    }

    public void createSimples(String userId, float montante,int idOdd) throws SQLException{
        this.apostas.createSimples(userId,montante ,idOdd);
    }

    public void createMultipla(String userId,float montante,List<Integer> listaOdd) throws SQLException{
        this.apostas.createMultipla(userId,montante,listaOdd);
    }

    public List<Aposta> getHistoricoApostas(String idUser) throws SQLException{
        return this.apostas.getHistoricoApostas(idUser);
    }

    public List<Aposta> getApostasAtivas (String idUser) throws SQLException{
        return this.apostas.getApostasAtivas(idUser);
    }
}
