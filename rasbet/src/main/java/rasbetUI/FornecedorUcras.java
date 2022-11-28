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
        map.put("e22b85618a26fefa8f6270c890998b96","Benfica");
        map.put("f03db53acb1492a8930663127323502e","Pacos de Ferreira");
        for (Game g: games.values()){
            if (g.concluido()) {
                String vencedor = g.vencedor();
                map.put(g.getId(),vencedor);
            }
        }
        return map;
    }
}
