package rasbetLN;

public class Favorito {
    private int idFavorito;
    private String favorito;

    public Favorito() {
        this.idFavorito = 0;
        this.favorito = null;
    }
    public Favorito(int idFavorito, String favorito) {
        this.idFavorito = idFavorito;
        this.favorito = favorito;
    }

    public Favorito(Favorito f) {
        this.idFavorito = f.getIdFavorito();
        this.favorito = f.getFavorito();
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public String getFavorito() {
        return favorito;
    }

    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }

    public Favorito clone() {
        return new Favorito(this);
    }
}


