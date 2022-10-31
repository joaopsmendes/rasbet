package rasbetLN;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jogo {
    private String idJogo;
    private Desporto desporto;
    private Map<String,Odd> mapOdds;
    private LocalDateTime data;
    private Estado estado;

    private enum Estado{
        ATIVO,SUSPENSO,FECHADO
    }


    public Jogo(String id, Desporto d, LocalDateTime commenceTime) {
        this.idJogo = id;
        this.desporto = d;
        this.mapOdds = new HashMap<>();
        this.data = commenceTime;
        this.estado= Estado.ATIVO;
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
}
