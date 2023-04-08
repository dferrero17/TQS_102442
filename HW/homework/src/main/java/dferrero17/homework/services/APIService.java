package dferrero17.homework.services;

import dferrero17.homework.api.openweather.OpenWeather;
import dferrero17.homework.api.openweather.OpenWeatherComponent;
import dferrero17.homework.api.openweather.OpenWeatherData;
import dferrero17.homework.api.openweather.OpenWeatherKey;
import dferrero17.homework.cache.Storage;
import dferrero17.homework.statistics.Stats;
import org.springframework.stereotype.Service;

import java.util.Comparator;


@Service
public class APIService {
    private OpenWeather api;
    private Storage<OpenWeatherKey, OpenWeatherData> cache;

    public APIService(OpenWeather api) {
        this.api = api;
        this.cache = new Storage<>(10_000L);  // 10 seconds
    }

    public OpenWeatherData getAirQuality(Double lat, Double lon) {
        OpenWeatherKey key = new OpenWeatherKey(lat, lon);
        OpenWeatherData data = cache.get(key);
        if (data == null) {
            Stats.getInstance().incrementCacheMisses();

            OpenWeatherData newData = api.call(lat, lon);
            if (newData == null) {
                Stats.getInstance().incrementApiFails();
                return null;
            }

            Stats.getInstance().incrementApiHits();
            cache.put(key, newData);
            return newData;
        }
        Stats.getInstance().incrementCacheHits();
        return data;
    }

    public OpenWeatherComponent getMostRecentAirQuality(Double lat, Double lon) {
        OpenWeatherData data = getAirQuality(lat, lon);
        if (data == null) {
            return null;
        }

        return data.getComponentList()
                .stream()
                .max(Comparator.comparing(OpenWeatherComponent::getDt))
                .orElse(null);
    }
}
