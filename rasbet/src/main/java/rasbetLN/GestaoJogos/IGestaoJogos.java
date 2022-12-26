package rasbetLN.GestaoJogos;

import rasbetLN.GestaoApostas.Odd;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGestaoJogos {

    void adicionarJogo(Jogo jogo) throws SQLException;

    public Map<String, Desporto> getDesportos () throws SQLException;


    Map<String, Jogo> getJogos(Desporto desporto) throws SQLException;

    Map<String, ApostaJogo> getApostasJogo(Jogo jogo) throws SQLException;

    boolean existeJogo(String gameId, Desporto desporto) throws SQLException;

    Map<Integer, List<Integer>> updateResultados(Map<String, String> map, Desporto desporto) throws SQLException;
    void alterarOdd(int idOdd, float valor) throws SQLException;

    void updateEstadoJogos() throws SQLException;

    void suspenderJogo(String idJogo) throws SQLException;

    void ativarJogo(String idJogo) throws SQLException;

    List<Integer> getOddsJogo(String idJogo) throws SQLException;

    String getTituloJogo(String idJogo) throws SQLException;

    String getIdJogoFromOdd(int idOdd) throws SQLException;


//    Desporto getDesporto(String idJogo) throws SQLException;
}
