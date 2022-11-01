package rasbetLN;

import java.sql.SQLException;
import java.util.Map;

public interface IGestaoJogos {

    void adicionarJogo(Jogo jogo);

    public Map<Integer,Desporto> getDesportos () throws SQLException;
}
