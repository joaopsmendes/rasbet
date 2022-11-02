package rasbetLN.GestaoUtilizadores;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private float valor;
    private float freeBets;
    private List<Transacao> listaTransacoes;

    public Carteira() {
        this.valor = 0;
        freeBets = 10;
        this.listaTransacoes  = null;
    }

    public float getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public float getFreeBets() {
        return freeBets;
    }

    public void setFreeBets(float freeBets) {
        this.freeBets = freeBets;
    }

    public List<Transacao> getListaTransacoes() {
        ArrayList<Transacao> lista = new ArrayList<>();
        for(Transacao t : this.listaTransacoes)
            lista.add(t.clone());
        return lista;
    }

    
}
