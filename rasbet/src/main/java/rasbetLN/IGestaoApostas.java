package rasbetLN;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface IGestaoApostas {
    void newAposta(int idAposta, float montante, LocalDateTime data, String email, boolean resultado) throws SQLException;
    void addAposta();
    Aposta getAposta();
}
