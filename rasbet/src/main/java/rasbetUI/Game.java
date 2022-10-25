package rasbetUI;


import java.util.Arrays;

public class Game {
    String id;
    String homeTeam;
    String awayTeam;
    String commenceTime;
    boolean completed;
    String scores;
    Bookmakers[] bookmakers;


    public String getId() {
        return id;
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

    public String getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = commenceTime;
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

    public Bookmakers[] getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(Bookmakers[] bookmakers) {
        this.bookmakers = bookmakers;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayteam='" + awayTeam + '\'' +
                ", commenceTime='" + commenceTime + '\'' +
                ", completed=" + completed +
                ", scores='" + scores + '\'' +
                ", bookmarkers=" + Arrays.toString(bookmakers) +
                '}';
    }
}
