package tqs.homework.api.openweather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Date;

@Service
public class OpenWeather {
    private final IOpenWeatherApi api;
    private final Logger logger = LoggerFactory.getLogger(OpenWeather.class);

    @Value("${openweather.apikey}")
    private String apiKey;

    public OpenWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/air_pollution/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(IOpenWeatherApi.class);
        logger.info("OpenWeather API created");
    }

    public OpenWeatherData call(Double lat, Double lon) {
        long end = new Date().getTime() / 1000;
        long start = end - 10 * 24 * 60 * 60; // 10 days

        try {
            logger.info("OpenWeather API called");
            return api.getHistory(lat, lon, start, end, apiKey).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("OpenWeather API failed");
            return null;
        }
    }
}
