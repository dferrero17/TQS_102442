package dferrero17.homework.services;

import dferrero17.homework.api.mapquest.MapQuest;
import dferrero17.homework.api.mapquest.MapQuestData;
import dferrero17.homework.api.mapquest.MapQuestKey;
import dferrero17.homework.cache.Storage;
import dferrero17.homework.statistics.Stats;
import org.springframework.stereotype.Service;


@Service
public class GeoCodingService {
    private MapQuest api;

    private Storage<MapQuestKey, MapQuestData> cache;


    public GeoCodingService(MapQuest api) {
        this.api = api;
        this.cache = new Storage<>(10_000L);  // 10 seconds
    }

    public MapQuestData getGeoCoding(Double lat, Double lon) {
        MapQuestKey key = new MapQuestKey(lat, lon);
        MapQuestData data = cache.get(key);
        if (data == null) {
            Stats.getInstance().incrementCacheMisses();

            MapQuestData newData = api.call(lat, lon);
            System.out.println(newData);
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
}
