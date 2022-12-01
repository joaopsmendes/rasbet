package rasbetUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOutput{

    private String idJogo;

    private String desporto;
    private LocalDateTime data;

    private String escolhido;
    private String titulo;
    private boolean concluido;
    private Map<String,List<ApostaOutput>> mapMercados;

    public GameOutput(){

    }
    public GameOutput (Game game){
        this.concluido = game.concluido();
        this.idJogo = game.getId();
        this.data = game.getHoraComeco();
        this.titulo = game.getTitulo();
        this.desporto = game.getDesporto();
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


    public String getDesporto() {
        return desporto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getEscolhido() {
        return escolhido;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public String getIdJogo() {
        return idJogo;
    }


    public String getTitulo() {
        return titulo;
    }

    public Map<String, List<ApostaOutput>> getMapMercados() {
        return mapMercados;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setEscolhido(String escolhido) {
        this.escolhido = escolhido;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public void setMapMercados(Map<String, List<ApostaOutput>> mapMercados) {
        this.mapMercados = mapMercados;
    }
}
