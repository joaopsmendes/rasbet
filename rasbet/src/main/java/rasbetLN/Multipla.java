package rasbetLN;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}


