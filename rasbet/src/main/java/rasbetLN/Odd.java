package rasbetLN;

public class Odd {
    private float valor;
    private String opcao;
    private Jogo jogo;

    public Odd(float valor, String opcao, Jogo jogo) {
        this.valor = valor;
        this.opcao = opcao;
        this.jogo = jogo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }


}
