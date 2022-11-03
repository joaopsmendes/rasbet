package rasbetLN.GestaoJogos;

import rasbetLN.GestaoApostas.Odd;

import java.util.HashMap;
import java.util.Map;

public class ApostaJogo {
    private String tema;
    private Map<String, Odd> mapOdd;
    private Odd resultado;


    public ApostaJogo(String tema) {
        this.tema = tema;
        mapOdd = new HashMap<>();
        resultado = null;
    }

    public ApostaJogo(ApostaJogo apostaJogo) {
        this.tema = apostaJogo.getTema();
        this.mapOdd = apostaJogo.getMapOdd();
        this.resultado = apostaJogo.getResultado();
    }

    public void addOdd(Odd odd){
        mapOdd.put(odd.getOpcao(),odd);
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Map<String, Odd> getMapOdd() {
        Map<String, Odd> map = new HashMap<>();
        for(Map.Entry<String, Odd> entry : mapOdd.entrySet()){
            this.mapOdd.put(entry.getKey(), entry.getValue().clone());
        }
        return map;
    }

    public void setMapOdd(Map<String, Odd> mapOdd) {
        Map<String, Odd> map = new HashMap<>();
        for(Map.Entry<String, Odd> entry : mapOdd.entrySet()){
            this.mapOdd.put(entry.getKey(), entry.getValue().clone());
        }
        this.mapOdd = map;
    }

    public Odd getResultado() {
        return resultado;
    }

    public void setResultado(Odd resultado) {
        this.resultado = resultado;
    }

    public ApostaJogo clone(){
        return new ApostaJogo(this);
    }
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}
