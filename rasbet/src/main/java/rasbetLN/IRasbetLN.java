package rasbetLN;

import rasbetUI.ApostaRequest;
import rasbetUI.Game;

public interface IRasbetLN {
    void addGame(Game game, String bookmaker, String desporto);



    // Class Apostador
    void addFavorito(String id,String desporto,String f);

    void apostaSimples(ApostaRequest apostaRequest);
    void apostaMultipla(ApostaRequest apostaRequest);


    }
