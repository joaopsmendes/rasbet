package rasbetLN;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestaoJogos {

    void adicionarJogo(Jogo jogo) throws SQLException;

    public Map<Integer,Desporto> getDesportos () throws SQLException;

    void alteraEstado(String idJogo, int estado) throws SQLException;

    List<Jogo> getJogo(Desporto desporto) throws SQLException;

    Map<String, ApostaJogo> getApostasJogo(Jogo jogo) throws SQLException;

    Map<String,Odd> oddApostaJogo(String tema, Jogo jogo) throws SQLException;
}
