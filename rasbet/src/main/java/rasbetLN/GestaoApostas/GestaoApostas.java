package rasbetLN.GestaoApostas;

import rasbetDB.DBAposta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestaoApostas implements IGestaoApostas {
    private DBAposta apostas;

    public GestaoApostas(Connection connection){
        this.apostas = new DBAposta(connection);
    }


    @Override
    public Aposta getAposta(int idAposta, String email) throws SQLException{
        return this.apostas.getAposta(idAposta, email);
    }


    public void createAposta(String userId,float montante, float ganhos, List<Integer> listaOdd) throws SQLException{
        if (listaOdd.size() == 1) this.apostas.createSimples(userId,montante,ganhos,listaOdd.get(0));
        else if (listaOdd.size() < 21) this.apostas.createMultipla(userId, montante, ganhos, listaOdd);
        else System.out.println("Nâo é possivel realizar a aposta");
    }

    @Override
    public float cashout(int idAposta) throws SQLException {
        apostas.cashout(idAposta);
        return (float) (0.5 * apostas.getMontante(idAposta));
    }

    @Override
    public Map<String, List<Float>> updateResultados(Map<Integer, List<Integer>> res) throws SQLException {
        return apostas.updateResulados(res);
    }

    public Set<String> getUtilizadoresOdd(int idOdd) throws SQLException {
        return apostas.getUtilizadoresOdd(idOdd);
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
