package rasbetLN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Apostador extends Utilizador {
    private Carteira carteira;
    private List<Favorito> listaFavoritos;
    private Map<Integer, Aposta> historicoApostas;
    private Map<Integer, Aposta> apostasAtivas;

    public Apostador(String email, String password, String dataNascimento, String NIF, String nome) {
        super(email, password, dataNascimento, NIF, nome);
        this.carteira = new Carteira();
        this.listaFavoritos = new ArrayList<>();
        this.historicoApostas = new HashMap<>();
        this.apostasAtivas = new HashMap<>();
    }


    public List<Favorito> getListaFavoritos() {
        ArrayList<Favorito> lista = new ArrayList<>();
        for(Favorito f : this.listaFavoritos)
            lista.add(f.clone());
        return lista;
    }
/*
    public Map<String, Aposta> getHistoricoApostas() {
        Map<String, Aposta> lista = new HashMap<>();
        for(Map.Entry<Integer, Aposta> e : this.historicoApostas.entrySet())
            lista.put(e.getKey(), e.getValue().clone());
        return lista;
    }

    public Map<String, Aposta> getApostasAtivas() {
        Map<String, Aposta> lista = new HashMap<>();
        for(Map.Entry<Integer, Aposta> e : this.apostasAtivas.entrySet())
            lista.put(e.getKey(), e.getValue().clone());
        return lista;
    }

    void addFavorito(String e) { //Equipa Coletiva ou Individual
        Favorito f = new Favorito(e);
        this.listaFavoritos.add(f);
    }

    public Apostador clone() {
        return new Apostador(this);
    }
 */




}
