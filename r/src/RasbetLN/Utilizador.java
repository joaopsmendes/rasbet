package src;

import java.util.List;

public abstract class Utilizador {
    private int id;
    private String email;
    private String password;
    private String dataNascimento;
    private String NIF;
    private List<Aposta> listaApostas;
    private List<Favorito> listaFavoritos;

}
