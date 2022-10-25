package rasbetLN;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Aposta {
    private int idAposta;
    private double montante;
    private LocalDateTime dataAposta;
    private List<Jogo> jogos;
}
