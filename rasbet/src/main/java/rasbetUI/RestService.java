package rasbetUI;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Game[] getPostsPlainJSON() {
        String url = "http://ucras.di.uminho.pt/v1/games/";
        return this.restTemplate.getForObject(url, Game[].class);
    }
}