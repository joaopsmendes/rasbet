package rasbetLN;

public abstract class Utilizador {
    private String email;
    private String password;
    private String dataNascimento;
    private String NIF;
    private String nome;


    public Utilizador() {
        this.email = null;
        this.password = null;
        this.dataNascimento = null;
        this.NIF = null;
        this.nome = null;

    }

    public Utilizador(String email, String password, String dataNascimento, String nif) {
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
        this.NIF = nif;
        this.nome = nome;

    }

    public Utilizador(Utilizador u){
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.dataNascimento = u.getDataNascimento();
        this.NIF = u.getNIF();
        this.nome = u.getNome();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
