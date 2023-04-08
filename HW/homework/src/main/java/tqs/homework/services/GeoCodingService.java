package tqs.homework.services;

import tqs.homework.api.mapquest.MapQuest;
import tqs.homework.api.mapquest.MapQuestData;
import tqs.homework.api.mapquest.MapQuestKey;
import tqs.homework.cache.Storage;
import tqs.homework.statistics.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class GeoCodingService {
    private MapQuest api;

    private Storage<MapQuestKey, MapQuestData> cache;
    private final Logger logger = LoggerFactory.getLogger(GeoCodingService.class);


    public GeoCodingService(MapQuest api) {
        this.api = api;
        this.cache = new Storage<>(10_000L);  // 10 seconds
        logger.info("GeoCoding Service created");
    }

    public MapQuestData getGeoCoding(Double lat, Double lon) {
        MapQuestKey key = new MapQuestKey(lat, lon);
        MapQuestData data = cache.get(key);
        if (data == null) {
            logger.info("Cache miss");
            Stats.getInstance().incrementCacheMisses();
            MapQuestData newData = api.call(lat, lon);
            if (newData != null) {
                logger.info("API hit");
                Stats.getInstance().incrementApiHits();
                cache.put(key, newData);
                return newData;
            } else {
                logger.info("API failed");
                Stats.getInstance().incrementApiFails();
                return null;
            }
        }
        logger.info("Cache hit");
        Stats.getInstance().incrementCacheHits();
        return data;
    }
}
