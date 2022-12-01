package rasbetLN.GestaoJogos;

import rasbetLN.GestaoApostas.Odd;

import java.util.ArrayList;
import java.util.List;

public class ApostaJogo {

    private int idApostaJogo;
    private String tema;
    private List<Odd> listOdd;
    private Odd resultado;


    public ApostaJogo(String tema) {
        this.tema = tema;
        listOdd = new ArrayList<>();
        resultado = null;
    }

    public ApostaJogo(ApostaJogo apostaJogo) {
        this.tema = apostaJogo.getTema();
        this.listOdd = apostaJogo.getListOdd();
        this.resultado = apostaJogo.getResultado();
    }

    public void addOdd(Odd odd){
        listOdd.add(odd);
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public List<Odd> getListOdd() {
        return listOdd;
    }

    public void setIdApostaJogo(int idApostaJogo) {
        this.idApostaJogo = idApostaJogo;
    }

    public void setListOdd(List<Odd> listOdd) {
        this.listOdd = listOdd;
    }

    public int getIdApostaJogo() {
        return idApostaJogo;
    }

    public Odd getResultado() {
        return resultado;
    }

    public void setResultado(Odd resultado) {
        this.resultado = resultado;
    }

    public ApostaJogo clone(){
        return new ApostaJogo(this);
    }
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}
