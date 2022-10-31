package rasbetLN;

import rasbetUI.Game;

import java.time.LocalDate;

public interface IRasbetLN {
    void addGame(Game game, String bookmaker, String desporto);



    // Class Apostador
    public void addFavorito(String id,String desporto,String f);

    }
