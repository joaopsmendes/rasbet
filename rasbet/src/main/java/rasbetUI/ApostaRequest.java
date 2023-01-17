package rasbetUI;

import java.util.Arrays;
import java.util.List;

public class ApostaRequest {

    String sessionId;
    float valor;
    float saldo;
    float freebets;
    float ganhoPossivel;
    Integer []odds;
    Integer promo;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public Integer getPromo() {
        return promo;
    }

    public void setPromo(Integer promo) {
        this.promo = promo;
    }
}
