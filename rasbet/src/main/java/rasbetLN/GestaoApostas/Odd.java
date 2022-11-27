package rasbetLN.GestaoApostas;

public class Odd {
    private int idOdd;
    private float valor;
    private String opcao;
    private String desJogo;

    public Odd(int idOdd,float valor, String opcao, String desJogo) {
        this.idOdd = idOdd;
        this.valor = valor;
        this.opcao = opcao;
        this.desJogo = desJogo;
    }

    public Odd(float valor, String opcao, String jogo) {
        this.valor = valor;
        this.opcao = opcao;
        this.desJogo = jogo;
    }

    public Odd(Odd odd) {
        this.valor = odd.getValor();
        this.opcao = odd.getOpcao();
        this.desJogo = odd.getDesJogo();
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

    public String getDesJogo() {
        return desJogo;
    }

    public Odd clone(){
        return new Odd(this);
    }


}
