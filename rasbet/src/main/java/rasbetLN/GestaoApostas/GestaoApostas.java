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



    public void createAposta(String userId,float montante,List<Integer> listaOdd) throws SQLException{
        if (listaOdd.size() == 1) this.apostas.createSimples(userId,montante,listaOdd.get(0));
        else this.apostas.createMultipla(userId,montante,listaOdd);
    }

    @Override
    public float cashout(int idAposta) throws SQLException {
        apostas.cashout(idAposta);
        return (float) (0.5 * apostas.getMontante(idAposta));
    }

    @Override
    public float fecharAposta(int id, boolean resultado) throws SQLException {
        apostas.fecharAposta(id,resultado);
        if (resultado){
            return apostas.getValorAposta(id);
        }

        return 0;
    }

    public List<Aposta> getHistoricoApostas(String idUser) throws SQLException{
        return this.apostas.getHistoricoApostas(idUser);
    }

    public List<Aposta> getApostasAtivas (String idUser) throws SQLException{
        return this.apostas.getApostasAtivas(idUser);
    }
}
