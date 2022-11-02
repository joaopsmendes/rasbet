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

    public Map<String, GameFutebol> getPostsPlainJSON() {
        String url = "http://ucras.di.uminho.pt/v1/games/";
        GameFutebol[] gameFutebols = this.restTemplate.getForObject(url, GameFutebol[].class);
        Map<String, GameFutebol> mapGame = new HashMap<>();
        for (GameFutebol g : gameFutebols){
            System.out.println(g.id);
            mapGame.put(g.id,g);
        }
        return mapGame;
    }
}