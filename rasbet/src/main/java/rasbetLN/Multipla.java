package rasbetLN;

import java.time.LocalDateTime;
import java.util.List;

public class Multipla extends Aposta {

    private List<Odd> oddList;

    public Multipla(float montante, List<Odd> oddList) {
        super(montante);
        this.oddList = oddList;
    }
}


