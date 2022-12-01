package rasbetUI;

import java.util.ArrayList;
import java.util.List;

public class ApostaOutput {

    private String tema;
    private List<Outcome> odds;

    public ApostaOutput(String tema, List<Outcome> odds) {
        this.tema = tema;
        this.odds = odds;
    }

    public String getTema() {
        return tema;
    }

    public List<Outcome> getOdds() {
        return odds;
    }

}
