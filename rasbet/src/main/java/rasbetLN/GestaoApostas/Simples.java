package rasbetLN.GestaoApostas;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Odd;

import java.time.LocalDate;

public class Simples extends Aposta {
    private Odd odd;


    public Simples(int idAposta, float montante, LocalDate data, boolean resultado){
        super(idAposta,montante,data,resultado);
    }
}
