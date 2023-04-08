package dferrero17.homework.api.mapquest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


@Service
public class MapQuest {
    private final IMapQuest api;

    @Value("${mapquest.apikey}")
    private String apiKey;

    public MapQuest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(IMapQuest.class);
    }

    public MapQuestData call(Double lat, Double lon) {
        try {
            return api.getLocation(apiKey, lat + "," + lon).execute().body();
        } catch (Exception e) {
            System.out.println("OI?????");
            e.printStackTrace();
            return null;
        }
    }
}
