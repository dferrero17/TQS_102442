package tqs.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tqs.homework.api.mapquest.MapQuest;
import tqs.homework.api.mapquest.MapQuestLocationData;
import tqs.homework.cache.Storage;


@Service
public class ReverseGeoCodingService {
    private MapQuest api;

    private Storage<String, MapQuestLocationData> cache;
    private final Logger logger = LoggerFactory.getLogger(ReverseGeoCodingService.class);


    public ReverseGeoCodingService(MapQuest api) {
        this.api = api;
        this.cache = new Storage<>(10_000L);  // 10 seconds
        logger.info("GeoCoding Service created");
    }

    public MapQuestLocationData getReverseGeoCoding(String location) {
        MapQuestLocationData data = cache.get(location);
        if (data == null) {
            logger.info("Cache miss");
            MapQuestLocationData newData = api.reverseCall(location);
            if (newData != null) {
                logger.info("API hit");
                cache.put(location, newData);
                return newData;
            } else {
                logger.info("API failed");
                return null;
            }
        }
        logger.info("Cache hit");
        return data;
    }
}
