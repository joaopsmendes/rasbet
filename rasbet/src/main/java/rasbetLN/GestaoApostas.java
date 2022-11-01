package rasbetLN;

import rasbetDB.DBAposta;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    public void createSimples(Aposta aposta, String userId, String idOdd) throws SQLException{
        this.apostas.createSimples(aposta,userId, idOdd);
    }

    public void createMultipla(Aposta aposta, String userId, List<String> listaOdd) throws SQLException{
        this.apostas.createMultipla(aposta,userId, listaOdd);
    }

    public void insereOdd(String idOdd, int idAposta) throws SQLException{
        this.apostas.insereOdd(idOdd, idAposta);
    }

    public void createAposta(Aposta aposta, String idUtilizador, int tipo) throws SQLException{
        this.apostas.createAposta(aposta, idUtilizador, tipo);
    }
}
