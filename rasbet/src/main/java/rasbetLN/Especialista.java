package rasbetLN;

import java.time.LocalDate;

public class Especialista extends Utilizador {
    public Especialista(){
        super();
    }


    public Especialista(String email, String password, LocalDate dataNascimento, String nif, String nome, String telemovel, String morada) {
        super(email, password, dataNascimento, nif, nome, telemovel, morada);
    }
}
