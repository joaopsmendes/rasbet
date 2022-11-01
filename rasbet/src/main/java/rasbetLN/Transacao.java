package rasbetLN;

import java.time.LocalDateTime;

public abstract class Transacao {

    private float valor;
    private LocalDateTime data;

    public Transacao(float valor) {
        this.valor = valor;
        this.data = LocalDateTime.now();
    }

    public Transacao(float valor, LocalDateTime data) {
        this.valor = valor;
        this.data = data;
    }

    public Transacao(Transacao transacao) {
        this.valor = transacao.getValor();
        this.data = transacao.getData();
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Transacao clone() {
        if (this instanceof Deposito) return new Deposito(this);
        else return new Levantamento(this);
    }
}
