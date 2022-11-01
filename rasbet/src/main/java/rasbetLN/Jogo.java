package rasbetLN;

import Exceptions.EstadoFechadoException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Jogo {
    private String idJogo;
    private Desporto desporto;
    private Map<String,Odd> mapOdds;
    private LocalDateTime data;
    private Estado estado;

    public enum Estado{
        ATIVO, //0
        SUSPENSO, //1
        FECHADO //2
    }


    public Jogo(String id, Desporto d, LocalDateTime commenceTime) {
        this.idJogo = id;
        this.desporto = d;
        this.mapOdds = new HashMap<>();
        this.data = commenceTime;
        this.estado= Estado.ATIVO;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    void addOdd(Odd odd){
        mapOdds.put(odd.getOpcao(),odd);
    }

    public String getIdJogo() {
        return idJogo;
    }

    public Odd getOdd(String oddValor){
        return mapOdds.get(oddValor);
    }

    public void updateEstado(Estado estado) throws EstadoFechadoException { //duvidas
        if(this.estado.equals(Estado.FECHADO)) throw new EstadoFechadoException("Estado de jogo: FECHADO");
        else if(this.estado.equals(Estado.SUSPENSO)) this.setEstado(Estado.ATIVO);
        else this.setEstado(Estado.SUSPENSO);
    }

}
