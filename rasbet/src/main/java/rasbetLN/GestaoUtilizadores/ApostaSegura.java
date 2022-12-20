package rasbetLN.GestaoUtilizadores;

public class ApostaSegura extends Promocao {
    private int limite;

    public ApostaSegura(int idPromocao, int limite){
        super(idPromocao);
        this.limite = limite;
    }

    public int getLimite() {
        return this.limite;
    }
}
