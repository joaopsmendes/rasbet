package rasbetLN;

import java.time.LocalDate;

public abstract class Utilizador {
    private int id;
    private String email;
    private String password;
    private String dataNascimento;
    private String NIF;

    public Utilizador() {
        this.id = 0;
        this.email = null;
        this.password = null;
        this.dataNascimento = null;
        this.NIF = null;
    }

    public Utilizador(int id, String email, String password, String dataNascimento, String nif) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
        this.NIF = nif;
    }

    public Utilizador(Utilizador u){
        this.id = u.getId();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.dataNascimento = u.getDataNascimento();
        this.NIF = u.getNIF();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }


}
