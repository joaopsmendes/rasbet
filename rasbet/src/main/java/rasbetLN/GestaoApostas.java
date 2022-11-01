package rasbetLN;

import rasbetDB.DBAposta;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GestaoApostas implements IGestaoApostas {
    private DBAposta apostas;

    public GestaoApostas(Connection connection){
        this.apostas = new DBAposta(connection);
    }


    @Override
    public void newAposta(int idAposta, float montante, LocalDateTime data, String email, boolean resultado) throws SQLException {

    }

    @Override
    public void addAposta() {

    }

    @Override
    public Aposta getAposta() {
        return null;
    }
}
