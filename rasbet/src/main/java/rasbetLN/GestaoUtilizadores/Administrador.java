package rasbetLN.GestaoUtilizadores;

import java.time.LocalDate;

public class Administrador extends Utilizador {
    public Administrador(String email, String password, LocalDate dataNascimento, String nif, String nome, String telemovel, String morada) {
        super(email, password, dataNascimento, nif, nome, telemovel, morada);
    }
}
