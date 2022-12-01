package rasbetLN.GestaoApostas;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestaoApostas {
    Aposta getAposta(int idAposta, String email) throws SQLException;
    List<Aposta> getHistoricoApostas(String idUser) throws SQLException;
    List<Aposta> getApostasAtivas(String idUser) throws SQLException;

    float fecharAposta(int id, boolean resultado) throws SQLException;

    void createAposta(String userId, float montante, float ganhos,List<Integer> listOdds) throws SQLException;

    float cashout(int idAposta) throws SQLException;

    Map<String, Float> updateResultados(Map<Integer, List<Integer>> res) throws SQLException;

}
