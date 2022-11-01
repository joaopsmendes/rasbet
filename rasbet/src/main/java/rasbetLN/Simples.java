package rasbetLN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Simples extends Aposta {
    private Odd odd;

    public Simples(float montante, Odd odd) {
        super(montante);
        this.odd = odd;
    }

    public Simples(int idAposta, float montante, LocalDate data, String idUtilizador, boolean resultado){


    }
}
