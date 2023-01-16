package rasbetUI;

import org.springframework.boot.web.client.RestTemplateBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FornecedorUcras implements Fornecedor{


    @Override
    public Map<String, Game> getGames() {
        RestService rest = new RestService(new RestTemplateBuilder());
        return rest.getGamesUcras();
    }

    public Map<String,String> updateResultados(){
        Map<String, Game> games = getGames();
        Map<String,String> map = new HashMap<>();
        for (Game g: games.values()){
            if (g.concluido()) {
                String vencedor = g.vencedor();
                map.put(g.getId(),vencedor);
                System.out.println(g.getId() + " " + vencedor);
            }
        }
        return map;
    }
}
