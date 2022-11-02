package rasbetLN.GestaoApostas;

import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoApostas.Odd;

import java.time.LocalDate;
import java.util.List;

public class Multipla extends Aposta {

    private List<Odd> oddList;

    public Multipla(float montante, List<Odd> oddList) {
        super(montante);
        this.oddList = oddList;
    }

    public Multipla(int idAposta, float montante, LocalDate data, String idUtilizador, boolean resultado) {
        super();
    }

    public float valorOdd(){
        float res = 0;
        for(Odd odd : oddList){
            res *= odd.getValor();
        }
        return res;
    }
}


