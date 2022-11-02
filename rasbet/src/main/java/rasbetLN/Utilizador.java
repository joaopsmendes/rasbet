package rasbetLN;

import java.time.LocalDate;

public abstract class Utilizador {
    private String email;
    private String password;
    private LocalDate dataNascimento;
    private String NIF;
    private String nome;
    private String telemovel;
    private String morada;


    public Utilizador() {
        this.email = null;
        this.password = null;
        this.dataNascimento = null;
        this.NIF = null;
        this.nome = null;
        this.telemovel = null;
        this.morada = null;

    }

    public Utilizador(String email, String password, LocalDate dataNascimento, String nif, String nome, String telemovel, String morada) {
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
        this.NIF = nif;
        this.nome = nome;
        this.telemovel = telemovel;
        this.morada = morada;

    }

    public Utilizador(Utilizador u){
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.dataNascimento = u.getDataNascimento();
        this.NIF = u.getNIF();
        this.nome = u.getNome();
        this.telemovel = u.getTelemovel();
        this.morada = u.getMorada();
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public  String getMorada(){
        return morada;
    }
}
