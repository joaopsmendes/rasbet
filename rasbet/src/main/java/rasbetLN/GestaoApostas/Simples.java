package rasbetLN.GestaoApostas;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Odd;

import java.time.LocalDate;
import java.util.List;

public class Simples extends Aposta {
    private Odd odd;


    public Simples(int idAposta, float montante, LocalDate data, Boolean resultado,Odd odd) {
        super(idAposta,montante,data,resultado);
        this.odd = odd;
    }

    public Odd getOdd() {
        return odd;
    }
}
