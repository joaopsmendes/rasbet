package rasbetUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOutput {

    private String idJogo;
    private LocalDateTime data;
    private String titulo;

    private Map<String,List<ApostaOutput>> mapMercados;


    public GameOutput (Game game){
        this.idJogo = game.getId();
        this.data = game.getHoraComeco();
        this.titulo = game.getTitulo();
        this.mapMercados =  new HashMap<>();
        for (Map.Entry<String, Map<String, List<Outcome>>> entry : game.getOdds().entrySet()){
            Map<String, List<Outcome>> map  = entry.getValue();
            mapMercados.putIfAbsent(entry.getKey(),new ArrayList<>());
            for (Map.Entry<String,List<Outcome>> entry2: map.entrySet()){
                ApostaOutput aposta = new ApostaOutput(entry2.getKey(),entry2.getValue());
                mapMercados.get(entry.getKey()).add(aposta);
            }
        }

    }



    public String getIdJogo() {
        return idJogo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getTitulo() {
        return titulo;
    }

    public Map<String, List<ApostaOutput>> getMapMercados() {
        return mapMercados;
    }
}
