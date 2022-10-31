package rasbetLN;

import rasbetUI.Game;
import rasbetUI.Outcome;

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
        // Adicionar à estrutura e BD

    }

    @Override
    public void addFavorito(String id,String desporto,String f) {
        Desporto d = desportoDisponiveis.get(desporto);
        Favorito fav = new Favorito(f,d);
        Utilizador utilizador = mapUtilizador.get(f);

    }
}
