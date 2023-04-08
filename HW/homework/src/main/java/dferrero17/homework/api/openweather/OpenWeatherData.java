package dferrero17.homework.api.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import dferrero17.homework.cache.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenWeatherData {
    private double latitude;
    private double longitude;
    private final Logger logger = LoggerFactory.getLogger(Storage.class);

    @JsonProperty("coord")
    private void unpackCoord(HashMap<String, Double> coord) {
        this.latitude = coord.get("lat");
        this.longitude = coord.get("lon");
        logger.info("OpenWeather Unpacked");
    }

    @JsonProperty("list")
    private List<OpenWeatherComponent> componentList = new ArrayList<>();

    @Override
    public String toString() {
        return "OpenWeatherEntity{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", componentList=" + componentList +
                '}';
    }
}
