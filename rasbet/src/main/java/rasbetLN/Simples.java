package rasbetLN;

import java.time.LocalDateTime;
import java.util.List;

public class Simples extends Aposta {
    public Simples() {
        super();
    }

    public Simples(int  idAposta, double montante, LocalDateTime dataAposta, List<Jogo> jogos) {
        super(idAposta, montante, dataAposta, jogos);
    }
}
