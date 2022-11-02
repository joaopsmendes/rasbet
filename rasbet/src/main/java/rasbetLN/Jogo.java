package rasbetLN;

import Exceptions.EstadoFechadoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Jogo {
    private String idJogo;
    private Desporto desporto;
    private LocalDateTime data;
    private Map<String,ApostaJogo> apostas;
    private Estado estado;

    public Jogo(String idJogo, Desporto desporto, LocalDateTime data, Estado estado) {
        this.idJogo = idJogo;
        this.desporto = desporto;
        this.data = data;
        this.estado = estado;
    }


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


    public Jogo(String idJogo, Desporto desporto, LocalDateTime data) {
        this.idJogo = idJogo;
        this.desporto = desporto;
        this.data = data;
        this.apostas = new HashMap<>();
        this.estado = Estado.ATIVO;
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

    public Collection<ApostaJogo> getApostas() {
        return apostas.values();
    }

    public void setApostas(Map<String, ApostaJogo> apostas) {//refazer
        this.apostas = apostas;
    }

    public void updateEstado(Estado estado) throws EstadoFechadoException { //duvidas
        if(this.estado.equals(Estado.FECHADO)) throw new EstadoFechadoException("Estado de jogo: FECHADO");
        else if(this.estado.equals(Estado.SUSPENSO)) this.setEstado(Estado.ATIVO);
        else this.setEstado(Estado.SUSPENSO);
    }

    public void addOdd(String tema, Odd odd) {
        apostas.putIfAbsent(tema,new ApostaJogo(tema));
        ApostaJogo apostaJogo = apostas.get(tema);
        apostaJogo.addOdd(odd);
    }


}
