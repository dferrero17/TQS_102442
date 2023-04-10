package tqs.homework.api.mapquest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


@Service
public class MapQuest {
    private final IMapQuest api;
    private final Logger logger = LoggerFactory.getLogger(MapQuest.class);

    @Value("${mapquest.apikey}")
    private String apiKey;

    public MapQuest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(IMapQuest.class);
        logger.info("MapQuest API created");
    }

    public MapQuestData call(Double lat, Double lon) {
        try {
            logger.info("MapQuest API called");
            return api.getLocation(apiKey, lat + "," + lon).execute().body();
        } catch (Exception e) {
            logger.info("MapQuest API failed");
            return null;
        }
    }

    public MapQuestLocationData reverseCall(String location) {
        try {
            logger.info("MapQuest API called");
            return api.getReverseLocation(apiKey, location).execute().body();
        } catch (Exception e) {
            logger.info("MapQuest API failed");
            return null;
        }
    }
}
