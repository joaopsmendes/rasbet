package rasbetLN;

import rasbetDB.DBJogos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GestaoJogos implements IGestaoJogos {
    private DBJogos jogos;

    public GestaoJogos(Connection connection){
        this.jogos = new DBJogos(connection);
    }

    @Override
    public void adicionarJogo(Jogo jogo) throws SQLException {
        jogos.adicionarJogo(jogo);
    }


    public Map<Integer,Desporto> getDesportos () throws SQLException {
        return jogos.getDesportos();
    }

    public void alteraEstado(String idJogo, int estado) throws SQLException{
        jogos.alteraEstado(idJogo, estado);
    }

    public List<Jogo> getJogo(Desporto desporto) throws SQLException{
        return this.jogos.getJogo(desporto);
    }

    public Map<String, ApostaJogo> getApostasJogo(Jogo jogo) throws SQLException{
        return this.jogos.apostasJogo(jogo);
    }

    public Map<String,Odd> oddApostaJogo(String tema, Jogo jogo) throws SQLException{
        return this.jogos.oddApostaJogo(tema, jogo);
    }
}
