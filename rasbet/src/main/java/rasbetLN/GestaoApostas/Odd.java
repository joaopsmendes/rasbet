package rasbetLN.GestaoApostas;

import rasbetLN.GestaoJogos.Jogo;

public class Odd {
    private int idOdd;
    private float valor;
    private String opcao;
    private String idJogo;

    public Odd(int idOdd,float valor, String opcao, String jogo) {
        this.idOdd = idOdd;
        this.valor = valor;
        this.opcao = opcao;
        this.idJogo = jogo;
    }

    public Odd(float valor, String opcao, String jogo) {
        this.valor = valor;
        this.opcao = opcao;
        this.idJogo = jogo;
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
