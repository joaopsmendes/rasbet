package rasbetLN;

import java.time.LocalDateTime;

public class Deposito extends Transacao {

    public Deposito(float valor) {
        super(valor);
    }


    public Deposito(Transacao transacao) {
        super(transacao.getValor(),transacao.getData());
    }
}
