package dferrero17.homework.api.mapquest;

import dferrero17.homework.cache.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


@Service
public class MapQuest {
    private final IMapQuest api;
    private final Logger logger = LoggerFactory.getLogger(Storage.class);

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
            System.out.println("OI?????");
            e.printStackTrace();
            logger.info("MapQuest API failed");
            return null;
        }
    }
}
