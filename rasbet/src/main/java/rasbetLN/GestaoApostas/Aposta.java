package rasbetLN.GestaoApostas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Aposta {

    private int idAposta;
    private float montante;
    private LocalDate dataAposta;
    boolean resultado;
    private List<Odd> listaOdd;

    public Aposta(int idAposta, float montante, LocalDate dataAposta, boolean resultado, List<Odd> listaOdd) {
        this.idAposta = idAposta;
        this.montante = montante;
        this.dataAposta = dataAposta;
        this.resultado = resultado;
        this.listaOdd = listaOdd;
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
        this.listaOdd = aposta.getListaOdd();
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

    public List<Odd> getListaOdd() {
        List<Odd> res = new ArrayList<>();
        for (Odd odd : listaOdd) {
            res.add(odd.clone());
        }
        return res;
    }




}
