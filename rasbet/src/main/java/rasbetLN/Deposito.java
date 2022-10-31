package rasbetLN;

import java.time.LocalDateTime;

public class Deposito extends Transacao {

    public Deposito(float valor, LocalDateTime data) {
        super(valor, data);
    }


    public Deposito(Transacao transacao) {
        super(transacao.getValor(),transacao.getData());
    }
}
