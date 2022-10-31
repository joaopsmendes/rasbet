package rasbetUI;

import java.util.Arrays;
import java.util.List;

public class ApostaRequest {

    String userId;
    float valor;
    ApostaOpcao []opcoes;

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

    public ApostaOpcao[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(ApostaOpcao[] opcoes) {
        this.opcoes = opcoes;
    }

    @Override
    public String toString() {
        return "ApostaRequest{" +
                "userId='" + userId + '\'' +
                ", valor=" + valor +
                ", opcoes=" + Arrays.toString(opcoes) +
                '}';
    }
}
