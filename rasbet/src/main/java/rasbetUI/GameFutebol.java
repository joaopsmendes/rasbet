package rasbetUI;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class GameFutebol implements Game{
    String id;
    String homeTeam;
    String awayTeam;
    LocalDateTime commenceTime;
    boolean completed;
    String scores;
    Map<String,Bookmaker> mapBookmakers;
    //Bookmaker[] bookmakers;

    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getHoraComeco() {
        return getCommenceTime();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = LocalDateTime.parse(commenceTime.substring(0,19));
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }


    public Map<String, Bookmaker> getMapBookmakers() {
        return mapBookmakers;
    }
    public void setBookmakers(Bookmaker[] bookmakers) {
        mapBookmakers = new HashMap<>();
        for (Bookmaker bookmaker : bookmakers){
            mapBookmakers.put(bookmaker.key,bookmaker);
        }
    }

    public Collection<Market> getMarkets(String bookmaker){
        Bookmaker b = mapBookmakers.get(bookmaker);
        return b.mapMarkets.values();
    }
    public String whoWon (){
        String[] goals = scores.split("x");
        int homeGoals = Integer.parseInt(goals[0]);
        int awayGoals = Integer.parseInt(goals[1]);
        if (homeGoals == awayGoals) return "Draw";
        if (homeGoals > awayGoals) return homeTeam;
        return awayTeam;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", commenceTime=" + commenceTime +
                ", completed=" + completed +
                ", scores='" + scores + '\'' +
                ", mapBookmakers=" + mapBookmakers +
                '}';
    }
}
