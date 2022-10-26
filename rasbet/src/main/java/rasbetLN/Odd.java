package rasbetLN;

public class Odd {
    private int idOdd;
    private  double valor;
    private String opcao;

    public Odd() {
        this.idOdd = 0;
        this.valor = 0;
        this.opcao = null;
    }

    public Odd(int idOdd, double valor, String opcao) {
        this.idOdd = idOdd;
        this.valor = valor;
        this.opcao = opcao;
    }

    public Odd(Odd o) {
        this.idOdd = o.getIdOdd();
        this.valor = o.getValor();
        this.opcao = o.getOpcao();
    }

    public int getIdOdd() {
        return idOdd;
    }

    public void setIdOdd(int idOdd) {
        this.idOdd = idOdd;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public Odd clone() {
        return new Odd(this);
    }


}
