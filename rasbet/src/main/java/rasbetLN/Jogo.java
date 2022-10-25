package rasbetLN;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private int idJogo;
    private Desporto desporto;
    private List<Odd> odds;
    private Resultado resultado;
    private LocalDateTime data;

    public Jogo() {
        this.idJogo = 0;
        this.desporto = null;
        this.odds = new ArrayList<>();
        this.resultado =  new Resultado();
        this.data = LocalDateTime.now();
    }

    public Jogo(int idJogo, Desporto desporto, List<Odd> odds, Resultado resultado, LocalDateTime data) {
        this.idJogo = idJogo;
        this.desporto = desporto;
        this.odds = odds;
        this.resultado = resultado;
        this.data = data;
    }

    public Jogo(Jogo jogo) {
        this.idJogo = jogo.getIdJogo();
        this.desporto = jogo.getDesporto();
        this.odds = jogo.getOdds();
        this.resultado = jogo.getResultado();
        this.data = jogo.getData();
    }

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public Desporto getDesporto() {
        return desporto;
    }

    public void setDesporto(Desporto desporto) {
        this.desporto = desporto;
    }

    public List<Odd> getOdds() {
        return odds;
    }

    public void setOdds(List<Odd> odds) {
        this.odds = odds;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
