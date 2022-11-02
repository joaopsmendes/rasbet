package rasbetUI;

import java.util.Arrays;
import java.util.List;

public class ApostaRequest {

    String userId;
    float valor;
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


    public Integer[] getOdds() {
        return odds;
    }

    public void setOdds(Integer[] odds) {
        this.odds = odds;
    }

    @Override
    public String toString() {
        return "ApostaRequest{" +
                "userId='" + userId + '\'' +
                ", valor=" + valor +
                ", odds=" + Arrays.toString(odds) +
                '}';
    }
}
