package rasbetLN;

public class Resultado {
    private int id;
    private  int golos;

    public Resultado() {
        this.id = 0;
        this.golos = 0;
    }

    public Resultado(int id, int golos) {
        this.id = id;
        this.golos = golos;
    }

    public Resultado(Resultado r) {
        this.id = r.getId();
        this.golos = r.getGolos();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGolos() {
        return golos;
    }

    public void setGolos(int golos) {
        this.golos = golos;
    }

    public Resultado clone() {
        return new Resultado(this);
    }
}
