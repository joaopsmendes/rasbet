package rasbetLN.GestaoApostas;

import java.sql.SQLException;
import java.util.List;

public interface IGestaoApostas {
    Aposta getAposta(int idAposta, String email) throws SQLException;
    List<Aposta> getHistoricoApostas(String idUser) throws SQLException;
    List<Aposta> getApostasAtivas(String idUser) throws SQLException;
    void createSimples(String userId, float montante,int idOdd) throws SQLException;

    void createMultipla(String userId,float montante,List<Integer> listaOdd) throws SQLException;

}
