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

    public Odd(Odd odd) {
        this.valor = odd.getValor();
        this.opcao = odd.getOpcao();
        this.idJogo = odd.getIdJogo();
    }

    public int getIdOdd() {
        return idOdd;
    }

    public float getValor() {
        return valor;
    }

    public String getOpcao() {
        return opcao;
    }

    public String getIdJogo() {
        return idJogo;
    }

    public Odd clone(){
        return new Odd(this);
    }


}
