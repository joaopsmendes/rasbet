package rasbetLN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Apostador extends Utilizador {
    private List<Aposta> listaApostas;
    private List<Favorito> listaFavoritos;
    private Map<String, Aposta> historicoApostas;
    private Map<String, Aposta> apostasAtivas;

    public Apostador() {
        super();
        this.listaApostas = new ArrayList<>();
        this.listaFavoritos = new ArrayList<>();
        this.historicoApostas = new HashMap<>();
        this.apostasAtivas = new HashMap<>();
    }

    public Apostador(int id, String email, String password, String dataNascimento, String NIF) {
        super(id, email, password, dataNascimento, NIF);
        this.listaApostas = new ArrayList<>();
        this.listaFavoritos = new ArrayList<>();
        this.historicoApostas = new HashMap<>();
        this.apostasAtivas = new HashMap<>();
    }

    public Apostador(Apostador a){
        super(a.getId(), a.getEmail(), a.getPassword(), a.getDataNascimento(), a.getNIF());
        this.listaApostas = a.getListaApostas();
        this.listaFavoritos = a.getListaFavoritos();
        this.historicoApostas = a.getHistoricoApostas();
        this.apostasAtivas = a.getApostasAtivas();
    }

    public List<Aposta> getListaApostas() {
        ArrayList<Aposta> lista = new ArrayList<>();
        for(Aposta a : this.listaApostas)
            lista.add(a.clone());
        return lista;
    }

    public void setListaApostas(List<Aposta> listaApostas) {
        this.listaApostas = new ArrayList<>();
        for(Aposta a : listaApostas)
            this.listaApostas.add(a.clone());
    }

    public List<Favorito> getListaFavoritos() {
        ArrayList<Favorito> lista = new ArrayList<>();
        for(Favorito f : this.listaFavoritos)
            lista.add(f.clone());
        return lista;
    }

    public void setListaFavoritos(List<Favorito> listaFavoritos) {
        this.listaFavoritos = new ArrayList<>();
        for(Favorito f : listaFavoritos)
            this.listaFavoritos.add(f.clone());
    }

    public Map<String, Aposta> getHistoricoApostas() {
        Map<String, Aposta> lista = new HashMap<>();
        for(Map.Entry<String, Aposta> e : this.historicoApostas.entrySet())
            lista.put(e.getKey(), e.getValue().clone());
        return lista;
    }

    public void setHistoricoApostas(Map<String, Aposta> historicoApostas) {
        this.historicoApostas = new HashMap<>();
        for(Map.Entry<String, Aposta> e : historicoApostas.entrySet())
            this.historicoApostas.put(e.getKey(), e.getValue().clone());
    }

    public Map<String, Aposta> getApostasAtivas() {
        Map<String, Aposta> lista = new HashMap<>();
        for(Map.Entry<String, Aposta> e : this.apostasAtivas.entrySet())
            lista.put(e.getKey(), e.getValue().clone());
        return lista;
    }

    public void setApostasAtivas(Map<String, Aposta> apostasAtivas) {
        this.apostasAtivas = new HashMap<>();
        for(Map.Entry<String, Aposta> e : apostasAtivas.entrySet())
            this.apostasAtivas.put(e.getKey(), e.getValue().clone());
    }

    public Apostador clone() {
        return new Apostador(this);
    }
}
