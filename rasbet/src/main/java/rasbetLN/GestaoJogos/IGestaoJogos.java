package rasbetLN.GestaoJogos;

import rasbetLN.GestaoApostas.Odd;

import java.sql.SQLException;
import java.util.Map;

public interface IGestaoJogos {

    void adicionarJogo(Jogo jogo) throws SQLException;

    public Map<String, Desporto> getDesportos () throws SQLException;

    void alteraEstado(String idJogo, Jogo.Estado estado) throws SQLException;

    Map<String, Jogo> getJogos(Desporto desporto) throws SQLException;

    Map<String, ApostaJogo> getApostasJogo(Jogo jogo) throws SQLException;

    Map<String, Odd> oddApostaJogo(String tema, Jogo jogo) throws SQLException;

    boolean existeJogo(String gameId, Desporto desporto) throws SQLException;

    void updateResultados(Map<String, String> map, Desporto desporto) throws SQLException;

    void alterarOdd(int idOdd, float valor) throws SQLException;
}
