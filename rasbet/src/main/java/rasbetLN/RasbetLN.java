package rasbetLN;

import org.springframework.web.bind.annotation.RequestBody;
import rasbetUI.ApostaOpcao;
import rasbetUI.ApostaRequest;
import rasbetUI.Game;
import rasbetUI.Outcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RasbetLN implements IRasbetLN{

    private Map<String,Desporto> desportoDisponiveis;//??? Not sure about this
    private Map<String,Map<String,Jogo>> jogos;
    private Map<String,Utilizador> mapUtilizador;


    @Override
    public void addGame(Game game, String bookmaker, String desporto) {
        Desporto d = desportoDisponiveis.get(desporto);
        Jogo jogo = new Jogo(game.getId(),d,game.getCommenceTime());
        for (Outcome outcome : game.getOutcomes(bookmaker)){
            Odd odd = new Odd(outcome.getPrice(),outcome.getName(),jogo);
            jogo.addOdd(odd);
        }
        Map<String,Jogo> mapJogos = jogos.get(desporto);
        mapJogos.put(jogo.getIdJogo(),jogo);
        // Adicionar à estrutura e BD

    }

    @Override
    public void addFavorito(String id,String desporto,String f) {
        Desporto d = desportoDisponiveis.get(desporto);
        Favorito fav = new Favorito(f,d);
        Apostador apostador = (Apostador) mapUtilizador.get(f);
        apostador.addToFavoritos(fav);
        //update DB

    }

    @Override
    public void apostaSimples(ApostaRequest apostaRequest) {
        Apostador apostador = (Apostador) mapUtilizador.get(apostaRequest.getUserId());
        ApostaOpcao apostaOpcao = apostaRequest.getOpcoes()[0];

        Jogo jogo = jogos.get(apostaOpcao.getDesporto()).get(apostaOpcao.getGameId());
        Odd odd = jogo.getOdd(apostaOpcao.getOpcao());

        Simples simples = new Simples(apostaRequest.getValor(),odd);
        apostador.addAposta(simples);

        //Add to DATABASE

    }

    public void apostaMultipla(ApostaRequest apostaRequest){
        Apostador apostador = (Apostador) mapUtilizador.get(apostaRequest.getUserId());
        List<Odd> listOdd = new ArrayList<>();
        for(ApostaOpcao apostaOpcao : apostaRequest.getOpcoes()){
            Jogo jogo = jogos.get(apostaOpcao.getDesporto()).get(apostaOpcao.getGameId());
            Odd odd = jogo.getOdd(apostaOpcao.getOpcao());
            listOdd.add(odd);
        }
        Multipla multipla = new Multipla(apostaRequest.getValor(),listOdd);
        apostador.addAposta(multipla);

        //ADD TO DATABASE
    }


}
