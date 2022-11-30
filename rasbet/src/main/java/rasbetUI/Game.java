package rasbetUI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface Game {

    String getId();

    LocalDateTime getHoraComeco();

    boolean concluido();

    Map<String, List<Outcome>> getOdds(String bookmaker);

    Map<String,Map<String, List<Outcome>>> getOdds();

    String vencedor();

    String getTitulo();

}
