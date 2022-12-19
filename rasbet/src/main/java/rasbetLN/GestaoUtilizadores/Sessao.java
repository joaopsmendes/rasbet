package rasbetLN.GestaoUtilizadores;
import java.util.UUID;

public class Sessao {
    private String idUser;
    private String idSession;

    public Sessao(String idUser) {
        this.idUser = idUser;
        this.idSession = generateSessionId();
    }

    public String getIdUser() {
        return this.idUser;
    }

    public String getIdSession() {
        return this.idSession;
    }

    public String generateSessionId() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("_", "");
    }
}
