package rasbetLN;

import rasbetDB.DBJogos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class GestaoJogos implements IGestaoJogos {
    private DBJogos jogos;

    public GestaoJogos(Connection connection){
        this.jogos = new DBJogos(connection);
    }

    @Override
    public void adicionarJogo(Jogo jogo) {
        jogos.adicionarJogo(jogo);
    }


    public Map<Integer,Desporto> getDesportos () throws SQLException {
        return jogos.getDesportos();
    }

    public void alteraEstado(String idJogo, int estado) throws SQLException{
        jogos.alteraEstado(idJogo, estado);
    }
}
