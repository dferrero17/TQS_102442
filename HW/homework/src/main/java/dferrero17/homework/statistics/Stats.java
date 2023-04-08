package dferrero17.homework.statistics;
import java.util.HashMap;

public class Stats {
    private static Stats instance = null;

    private Stats() {}

    public static Stats getInstance() {
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    private int cacheMisses = 0;
    private int cacheHits = 0;

    private int apiHits = 0;
    private int apiFails = 0;

    public void incrementCacheMisses() {
        cacheMisses++;
    }

    public void incrementCacheHits() {
        cacheHits++;
    }

    public void incrementApiFails() {
        apiFails++;
    }

    public void incrementApiHits() {
        apiHits++;
    }

    // reset method
    public void reset() {
        cacheMisses = 0;
        cacheHits = 0;
        apiHits = 0;
        apiFails = 0;
    }

    // convert to hashmap
    public HashMap<String, Integer> toHashMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("cacheMisses", cacheMisses);
        map.put("cacheHits", cacheHits);
        map.put("apiHits", apiHits);
        map.put("apiFails", apiFails);
        return map;
    }
}
