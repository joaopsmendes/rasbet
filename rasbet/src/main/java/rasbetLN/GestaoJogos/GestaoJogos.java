package rasbetLN.GestaoJogos;

import rasbetDB.DBJogos;
import rasbetLN.GestaoApostas.Odd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public Map<String, Desporto> getDesportos () throws SQLException {
        return jogos.getDesportos();
    }


    public Map<String, Jogo> getJogos(Desporto desporto) throws SQLException{
        return this.jogos.getJogos(desporto);
    }

    public Map<String, ApostaJogo> getApostasJogo(Jogo jogo) throws SQLException{
        return this.jogos.apostasJogo(jogo);
    }

    @Override
    public boolean existeJogo(String gameId, Desporto desporto) throws SQLException {
        return this.jogos.existeJogo(gameId,desporto);
    }

    @Override
    public Map<Integer, List<Integer>> updateResultados(Map<String, String> map, Desporto desporto) throws SQLException {
        Map <Integer, List<Integer>> res = new HashMap<>();
        res.put(0,new ArrayList<>());
        res.put(1,new ArrayList<>());
        for (Map.Entry<String, String> entry : map.entrySet()){
            if(jogos.existeJogo(entry.getKey(),desporto)) {
                System.out.println(entry.getValue());
                Map <Integer, List<Integer>> m = jogos.updateResultado(entry.getKey(), entry.getValue());
                res.get(0).addAll(m.get(0));
                res.get(1).addAll(m.get(1));
            }
        }
        return res;
    }

    @Override
    public void alterarOdd(int idOdd, float valor) throws SQLException {
        this.jogos.updateOdd(idOdd,valor);
    }

    @Override
    public void updateEstadoJogos() throws SQLException {
        jogos.updateEstadoJogos();

    }

    @Override
    public void suspenderJogo(String idJogo) throws SQLException {
        jogos.alteraEstado(idJogo, Jogo.Estado.SUSPENSO.value);
    }

    @Override
    public void ativarJogo(String idJogo) throws SQLException {
        jogos.alteraEstado(idJogo, Jogo.Estado.ATIVO.value);
    }

    public List<Integer> getOddsJogo(int idOdd) throws SQLException{
        return jogos.getOddsJogo(idOdd);
    }

    public String getTituloJogo(int idOdd) throws SQLException{
        return jogos.getTituloJogo(idOdd);
    }


//    @Override
//    public Desporto getDesporto(String idJogo) throws SQLException {
//        return this.jogos.getDesporto(idJogo);
//    }
}
