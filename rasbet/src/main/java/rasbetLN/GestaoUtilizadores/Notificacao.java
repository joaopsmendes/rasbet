package rasbetLN.GestaoUtilizadores;

public class Notificacao {
    private int idNotificacao;
    private String conteudo;
    private boolean vista;

    public Notificacao(String conteudo, boolean vista){
        this.conteudo = conteudo;
        this.vista = vista;
    }

    public Notificacao(int idNotificacao, String conteudo, boolean vista){
        this.idNotificacao = idNotificacao;
        this.conteudo = conteudo;
        this.vista = vista;
    }

    public Notificacao(Notificacao n){
        this.idNotificacao = n.getIdNotificacao();
        this.conteudo = n.getConteudo();
        this.vista = n.isVista();
    }

    public int getIdNotificacao() {
        return idNotificacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public boolean isVista() {
        return vista;
    }

    public Notificacao clone(){
        return new Notificacao(this);
    }
}
