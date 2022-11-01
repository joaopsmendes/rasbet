package rasbetLN;

import java.time.LocalDateTime;

public class Levantamento extends Transacao {

    public Levantamento(float valor, LocalDateTime data) {
        super(valor, data);
    }

    public Levantamento(float valor){
        super(valor);
    }

    public Levantamento(Transacao transacao) {
        super(transacao.getValor(),transacao.getData());
    }
}
