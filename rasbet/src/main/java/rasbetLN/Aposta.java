package rasbetLN;
import java.time.LocalDateTime;

public abstract class Aposta {
    private static int idAposta=0;
    private float montante;
    private LocalDateTime dataAposta;
    boolean resultado;

    private static int GetId() {
        return idAposta++;
    }

    public Aposta(float montante) {
        this.idAposta = GetId();
        this.montante = montante;
        this.dataAposta = LocalDateTime.now();
        resultado = false;
    }
    public Aposta() {
        this.idAposta = 0;
        this.montante = 0;
        this.dataAposta = LocalDateTime.now();
    }

    public Aposta(Aposta aposta) {
        this.idAposta = aposta.getIdAposta();
        this.montante = aposta.getMontante();
        this.dataAposta = aposta.getDataAposta();
    }

    public int getIdAposta() {
        return idAposta;
    }

    public float getMontante() {
        return montante;
    }

    public LocalDateTime getDataAposta() {
        return dataAposta;
    }

    public Aposta clone() {
        return new Aposta(this);
    }


}
