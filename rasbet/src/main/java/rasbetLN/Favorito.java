package rasbetLN;

public class Favorito {
    private String nome;
    private Desporto desporto;

    public Favorito(String nome, Desporto desporto) {
        this.nome = nome;
        this.desporto = desporto;
    }

    public Favorito(Favorito f) {
        this.nome = f.nome;
        this.desporto = f.desporto;
    }



    public Favorito clone() {
        return new Favorito(this);
    }
}


