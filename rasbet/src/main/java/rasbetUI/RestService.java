package rasbetUI;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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

    public Map<String,Game> getPostsPlainJSON() {
        String url = "http://ucras.di.uminho.pt/v1/games/";
        Game[] games = this.restTemplate.getForObject(url, Game[].class);
        Map<String,Game> mapGame = new HashMap<>();
        for (Game g : games){
            mapGame.put(g.id,g);
        }
        return mapGame;
    }
}