package rasbetLN;

public class Carteira {
    private float valor;
    private float freeBets;

    public Carteira() {
        this.valor = 0;
        freeBets = 10;
    }

    public float getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public float getFreeBets() {
        return freeBets;
    }

    public void setFreeBets(float freeBets) {
        this.freeBets = freeBets;
    }
}
