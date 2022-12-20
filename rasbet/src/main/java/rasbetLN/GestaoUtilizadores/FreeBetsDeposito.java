package rasbetLN.GestaoUtilizadores;

public class FreeBetsDeposito extends Promocao {
    private int deposito;
    private int freeBets;

    public FreeBetsDeposito(int idPromocao, int deposito, int freeBets){
        super(idPromocao);
        this.deposito = deposito;
        this.freeBets = freeBets;
    }

    public int getDeposito() {
        return this.deposito;
    }

    public int getFreeBets() {
        return this.freeBets;
    }

}
