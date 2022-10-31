package rasbetLN;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private String idJogo;
    private Desporto desporto;
    private List<Odd> odds;
    private LocalDateTime data;
    private Estado estado;

    private enum Estado{
        ATIVO,SUSPENSO,FECHADO
    }


    public Jogo(String id, Desporto d, LocalDateTime commenceTime) {
        this.idJogo = id;
        this.desporto = d;
        this.odds = new ArrayList<>();
        this.data = commenceTime;
        this.estado= Estado.ATIVO;
    }


    void addOdd(Odd odd){
        odds.add(odd);
    }




}
