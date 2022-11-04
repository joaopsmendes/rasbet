package rasbetUI;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Game> getGamesUcras() {
        String url = "http://ucras.di.uminho.pt/v1/games/";
        GameUcras[] gameUcras = this.restTemplate.getForObject(url, GameUcras[].class);
        Map<String, Game> mapGame = new HashMap<>();
        for (GameUcras g : gameUcras){
            System.out.println(g.id);
            mapGame.put(g.id,g);
        }
        return mapGame;
    }
}