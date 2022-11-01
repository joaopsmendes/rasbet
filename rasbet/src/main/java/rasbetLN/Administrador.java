package rasbetLN;

import java.time.LocalDate;

public class Administrador extends Utilizador{
    public Administrador(String email, String password, LocalDate dataNascimento, String nif, String nome) {
        super(email, password, dataNascimento, nif, nome);
    }
}
