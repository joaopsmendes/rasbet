package rasbetUI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bookmaker {
    String key;
    String lastUpdate;
    //Market[] markets;
    Map<String,Market> mapMarkets;

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


    public void setMarkets(Market[] markets) {
        mapMarkets = new HashMap<>();
        for (Market market : markets){
            mapMarkets.put(market.key,market);
        }
    }

    public Map<String, Market> getMapMarkets() {
        return mapMarkets;
    }

    public void setMapMarkets(Map<String, Market> mapMarkets) {
        this.mapMarkets = mapMarkets;
    }

    @Override
    public String toString() {
        return "Bookmaker{" +
                "key='" + key + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", mapMarkets=" + mapMarkets +
                '}';
    }
}
