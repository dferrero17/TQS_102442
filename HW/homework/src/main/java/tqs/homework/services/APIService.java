package tqs.homework.services;

import tqs.homework.api.openweather.OpenWeather;
import tqs.homework.api.openweather.OpenWeatherComponent;
import tqs.homework.api.openweather.OpenWeatherData;
import tqs.homework.api.openweather.OpenWeatherKey;
import tqs.homework.cache.Storage;
import tqs.homework.statistics.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;


@Service
public class APIService {
    private OpenWeather api;
    private final Storage<OpenWeatherKey, OpenWeatherData> cache;
    private final Logger logger = LoggerFactory.getLogger(APIService.class);

    public APIService(OpenWeather api) {
        this.api = api;
        this.cache = new Storage<>(10_000L);  // 10 seconds
        logger.info("API Service created");
    }

    public OpenWeatherData getAirQuality(Double lat, Double lon) {
        OpenWeatherKey key = new OpenWeatherKey(lat, lon);
        OpenWeatherData data = cache.get(key);
        if (data == null) {
            logger.info("Cache miss");
            Stats.getInstance().incrementCacheMisses();
            OpenWeatherData newData = api.call(lat, lon);
            if (newData == null) {
                logger.info("API failed");
                Stats.getInstance().incrementApiFails();
                return null;
            }
            logger.info("API hit");
            Stats.getInstance().incrementApiHits();
            cache.put(key, newData);
            return newData;
        }
        logger.info("Cache hit");
        Stats.getInstance().incrementCacheHits();
        return data;
    }

    public OpenWeatherComponent getMostRecentAirQuality(Double lat, Double lon) {
        OpenWeatherData data = getAirQuality(lat, lon);
        if (data == null) {
            logger.info("Most Recent Air Quality API failed");
            return null;
        }
        logger.info("Most Recent Air Quality API hit");
        return data.getComponentList()
                .stream()
                .max(Comparator.comparing(OpenWeatherComponent::getDt))
                .orElse(null);
    }
}
