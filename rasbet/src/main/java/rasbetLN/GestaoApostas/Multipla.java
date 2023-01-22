package rasbetLN.GestaoApostas;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Odd;

import java.time.LocalDate;
import java.util.List;

public class Multipla extends Aposta {

    private List<Odd> oddList;

    public Multipla(int idAposta, float montante, LocalDate data, Boolean resultado, float ganhoPossivel, List<Odd> oddList) {
        super(idAposta,montante,data,resultado, ganhoPossivel);
        this.oddList = oddList;
    }


    public List<Odd> getOddList() {
        return oddList;
    }

    public float valorOdd(){
        float res = 0;
        for(Odd odd : oddList){
            res *= odd.getValor();
        }
        return res;
    }
}


