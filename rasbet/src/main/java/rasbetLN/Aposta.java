package rasbetLN;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Aposta {
    private int idAposta;
    private double montante;
    private LocalDateTime dataAposta;
    private List<Jogo> jogos;

    public Aposta() {
        this.idAposta = 0;
        this.montante = 0;
        this.dataAposta = LocalDateTime.now();
        this.jogos = null;
    }

    public Aposta(int idAposta, double montante, LocalDateTime dataAposta, List<Jogo> jogos) {
        this.idAposta = idAposta;
        this.montante = montante;
        this.dataAposta = dataAposta;
        this.jogos = jogos;
    }

    public Aposta(Aposta aposta) {
        this.idAposta = aposta.getIdAposta();
        this.montante = aposta.getMontante();
        this.dataAposta = aposta.getDataAposta();
        this.jogos = aposta.getJogos();
    }

    public int getIdAposta() {
        return idAposta;
    }

    public void setIdAposta(int idAposta) {
        this.idAposta = idAposta;
    }

    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public LocalDateTime getDataAposta() {
        return dataAposta;
    }

    public void setDataAposta(LocalDateTime dataAposta) {
        this.dataAposta = dataAposta;
    }

    public List<Jogo> getJogos() {
        ArrayList<Jogo> jogos = new ArrayList<>();
        for(Jogo j : this.jogos)
            jogos.add(j.clone());
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = new ArrayList<>();
        for(Jogo j : jogos)
            this.jogos.add(j.clone());
    }

    public Aposta clone() {
        return new Aposta(this);
    }
}
