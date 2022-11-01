package rasbetLN;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IGestaoApostas {
    Aposta getAposta(int idAposta, String email) throws SQLException;
    void createSimples(Aposta aposta, String userId, String idOdd) throws SQLException;
    void createMultipla(Aposta aposta, String userId, List<String> listaOdd) throws SQLException;
    void insereOdd(String idOdd, int idAposta) throws SQLException;
    void createAposta(Aposta aposta, String idUtilizador, int tipo) throws SQLException;
}
