package rasbetLN.GestaoJogos;

import rasbetLN.GestaoApostas.Odd;

import java.time.LocalDateTime;
import java.util.*;

public class Jogo {
    private String idJogo;
    private String titulo;
    private Desporto desporto;
    private LocalDateTime data;
    private Map<String, ApostaJogo> apostas;
    private Estado estado;


    public enum Estado{
        ATIVO(0), //0
        SUSPENSO(1) , //1
        FECHADO(2) //2
        ;

        public final int value;
        Estado(int i) {
            this.value = i;
        }
    }

    public Jogo(String idJogo, Desporto desporto, LocalDateTime data, String titulo) {
        this.idJogo = idJogo;
        this.desporto = desporto;
        this.data = data;
        this.estado = estado;
        this.titulo = titulo;
        this.apostas = new HashMap<>();
    }



    public String getTitulo() {
        return titulo;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public int getEstadoValue(){
        return estado.value;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getIdJogo() {
        return idJogo;
    }

    public int getDesporto(){
        return desporto.getIdDesporto();
    }

    public LocalDateTime getData(){
        return data;
    }

    public Map<String, ApostaJogo> getApostas() {
        return apostas;
    }



    public void addOdd(String tema, Odd odd) {
        apostas.putIfAbsent(tema,new ApostaJogo(tema));
        ApostaJogo apostaJogo = apostas.get(tema);
        apostaJogo.addOdd(odd);
    }


    public void setIdApostaJogo(String tema, int idApostaJogo) {
        ApostaJogo apostaJogo = apostas.get(tema);
        apostaJogo.setIdApostaJogo(idApostaJogo);
    }

}
