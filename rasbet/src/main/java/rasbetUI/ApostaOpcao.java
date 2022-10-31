package rasbetUI;

public class ApostaOpcao{
    String gameId;
    String desporto;
    String opcao;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDesporto() {
        return desporto;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    @Override
    public String toString() {
        return "ApostaJogo{" +
                "gameId='" + gameId + '\'' +
                ", desporto='" + desporto + '\'' +
                ", opcao='" + opcao + '\'' +
                '}';
    }
}
