package rasbetUI;

import java.util.Arrays;

public class Bookmakers {
    String key;
    String lastUpdate;
    Market[] markets;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Market[] getMarkets() {
        return markets;
    }

    public void setMarkets(Market[] markets) {
        this.markets = markets;
    }

    @Override
    public String toString() {
        return "Bookmakers{" +
                "key='" + key + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", markets=" + Arrays.toString(markets) +
                '}';
    }
}
