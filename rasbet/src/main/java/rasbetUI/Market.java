package rasbetUI;


import java.util.Arrays;

public class Market {
    String key;
    Outcome[] outcomes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Outcome[] getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Outcome[] outcomes) {
        this.outcomes = outcomes;
    }

    @Override
    public String toString() {
        return "Market{" +
                "key='" + key + '\'' +
                ", outcomes=" + Arrays.toString(outcomes) +
                '}';
    }
}
