package tqs.homework.statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Stats {
    private static Stats instance = null;
    private final Logger logger = LoggerFactory.getLogger(Stats.class);

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
        logger.info("Cache Misses incremented");
        cacheMisses++;
    }

    public void incrementCacheHits() {
        logger.info("Cache Hits incremented");
        cacheHits++;
    }

    public void incrementApiFails() {
        logger.info("API Fails incremented");
        apiFails++;
    }

    public void incrementApiHits() {
        logger.info("API Hits incremented");
        apiHits++;
    }

    // reset method
    public void reset() {
        logger.info("Stats reset");
        cacheMisses = 0;
        cacheHits = 0;
        apiHits = 0;
        apiFails = 0;
    }

    // convert to hashmap
    public Map<String, Integer> toHashMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("cacheMisses", cacheMisses);
        map.put("cacheHits", cacheHits);
        map.put("apiHits", apiHits);
        map.put("apiFails", apiFails);
        logger.info("Stats packaged");
        return map;
    }
}
