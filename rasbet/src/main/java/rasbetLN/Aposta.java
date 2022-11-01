package rasbetLN;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Aposta {
    private static int idAposta=0;
    private float montante;
    private LocalDate dataAposta;
    boolean resultado;


    private static int GetId() {
        return idAposta++;
    }

    public Aposta(float montante) {
        this.idAposta = GetId();
        this.montante = montante;
        this.dataAposta = LocalDate.now();
        resultado = false;
    }
    public Aposta() {
        this.idAposta = 0;
        this.montante = 0;
        this.dataAposta = LocalDate.now();
    }

    public Aposta(Aposta aposta) {
        this.idAposta = aposta.getIdAposta();
        this.montante = aposta.getMontante();
        this.dataAposta = aposta.getDataAposta();
        this.resultado = aposta.getResultado();
    }

    public int getIdAposta() {
        return idAposta;
    }

    public float getMontante() {
        return montante;
    }

    public LocalDate getDataAposta() {
        return dataAposta;
    }

    public boolean getResultado(){
        return resultado;
    }





}
