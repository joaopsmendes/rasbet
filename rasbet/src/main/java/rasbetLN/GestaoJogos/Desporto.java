package rasbetLN.GestaoJogos;

public class Desporto {
    private String modalidade;
    private int idDesporto;

    public Desporto(int idDesporto,String modalidade) {
        this.modalidade = modalidade;
        this.idDesporto = idDesporto;
    }

    public int getIdDesporto() {
        return idDesporto;
    }
}
