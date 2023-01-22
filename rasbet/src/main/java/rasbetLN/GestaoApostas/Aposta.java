package rasbetLN.GestaoApostas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Aposta {

    private int idAposta;
    private float montante;
    private LocalDate dataAposta;
    Boolean resultado;

    private float ganhoPossivel;


    public Aposta(int idAposta, float montante, LocalDate dataAposta, Boolean resultado, float ganhoPossivel) {
        this.idAposta = idAposta;
        this.montante = montante;
        this.dataAposta = dataAposta;
        this.resultado = resultado;
        this.ganhoPossivel = ganhoPossivel;
    }

    public Aposta() {
        this.idAposta = 0;
        this.montante = 0;
        this.dataAposta = LocalDate.now();
    }

    public Aposta(Aposta aposta) {
        this.idAposta = aposta.getIdAposta();
        this.montante = aposta.getMontante();
        this.dataAposta = aposta.getDataAposta();
        this.resultado = aposta.getResultado();
        this.ganhoPossivel = aposta.getGanhoPossivel();
    }

    public int getIdAposta() {
        return idAposta;
    }

    public float getMontante() {
        return montante;
    }

    public LocalDate getDataAposta() {
        return dataAposta;
    }

    public Boolean getResultado(){
        return resultado;
    }

    public float getGanhoPossivel() {
        return ganhoPossivel;
    }





}
