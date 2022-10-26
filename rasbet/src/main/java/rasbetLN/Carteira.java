package rasbetLN;

import java.time.LocalDateTime;

public class Carteira {
    private int valor;
    private LocalDateTime data;

    public Carteira() {
        this.valor = 0;
        this.data = LocalDateTime.now();
    }

    public Carteira(int valor, LocalDateTime data) {
        this.valor = valor;
        this.data = data;
    }

    public Carteira(Carteira carteira){
        this.valor = carteira.getValor();
        this.data = carteira.getData();
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
