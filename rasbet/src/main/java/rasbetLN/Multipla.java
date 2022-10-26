package rasbetLN;

import java.time.LocalDateTime;
import java.util.List;

public class Multipla extends Aposta{

    public Multipla() {
        super();
    }

    public Multipla(int  idAposta, double montante, LocalDateTime dataAposta, List<Jogo> jogos) {
        super(idAposta, montante, dataAposta, jogos);
    }
}
