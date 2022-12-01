package rasbetUI;

import java.util.Arrays;
import java.util.List;

public class ApostaRequest {

    String userId;
    float valor;
    float saldo;
    float freebets;
    float ganhoPossivel;
    Integer []odds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void setFreebets(float freebets) {
        this.freebets = freebets;
    }

    public Integer[] getOdds() {
        return odds;
    }

    public void setOdds(Integer[] odds) {
        this.odds = odds;
    }

    public float getSaldo() {
        return saldo;
    }

    public float getGanhoPossivel() {
        return ganhoPossivel;
    }

    public void setGanhoPossivel(float ganhoPossivel) {
        this.ganhoPossivel = ganhoPossivel;
    }

    public float getFreebets() {
        return freebets;
    }
}
