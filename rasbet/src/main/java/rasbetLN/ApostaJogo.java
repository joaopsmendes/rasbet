package rasbetLN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApostaJogo {
    private String tema;
    private Map<String,Odd> mapOdd;
    private Odd resultado;


    public ApostaJogo(String tema) {
        this.tema = tema;
        mapOdd = new HashMap<>();
        resultado = null;
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
        return mapOdd;
    }

    public void setMapOdd(Map<String, Odd> mapOdd) {
        this.mapOdd = mapOdd;
    }

    public Odd getResultado() {
        return resultado;
    }

    public void setResultado(Odd resultado) {
        this.resultado = resultado;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
