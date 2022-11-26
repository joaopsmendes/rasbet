package rasbetLN.GestaoApostas;

import java.time.LocalDate;

public abstract class Aposta {

    private int idAposta;
    private float montante;
    private LocalDate dataAposta;
    boolean resultado;


    public Aposta(int idAposta, float montante, LocalDate dataAposta, boolean resultado) {
        this.idAposta = idAposta;
        this.montante = montante;
        this.dataAposta = dataAposta;
        this.resultado = resultado;
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

    public boolean getResultado(){
        return resultado;
    }





}
