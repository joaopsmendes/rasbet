package rasbetLN.GestaoUtilizadores;

import java.time.LocalDateTime;

public class Transacao {

    private float saldo;
    private float freebets;
    private LocalDateTime data;

    private Tipo tipo;

    public enum Tipo{
        CRIACAO_CONTA(0),
        DEPOSITO(1),
        LEVANTAMENTO(2),
        APOSTA(3),
        GANHO_APOSTA(4),
        PROMOCAO(5),
        BONUS_SEGUIDAS(6);

        public final int value;
        Tipo(int i) {
            this.value = i;
        }

    }


    public Transacao(float valor) {
        this.saldo = valor;
        this.data = LocalDateTime.now();
    }

    public Transacao(float saldo, float freebets, LocalDateTime data, Tipo tipo) {
        this.saldo = saldo;
        this.freebets = freebets;
        this.data = data;
        this.tipo = tipo;
    }

    public Transacao(float valor, LocalDateTime data) {
        this.saldo = valor;
        this.data = data;
    }

    public Transacao(Transacao transacao) {
        this.saldo = transacao.getSaldo();
        this.data = transacao.getData();
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getFreebets() {
        return freebets;
    }

    public Tipo getTipo() {
        return tipo;
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
