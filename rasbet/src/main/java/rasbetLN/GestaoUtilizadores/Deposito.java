package rasbetLN.GestaoUtilizadores;

import java.time.LocalDateTime;

public class Deposito extends Transacao {

    public Deposito(float valor) {
        super(valor);
    }

    public Deposito(float valor, LocalDateTime data){
        super(valor, data);
    }

    public Deposito(Transacao transacao) {
        super(transacao.getSaldo(),transacao.getData());
    }
}
