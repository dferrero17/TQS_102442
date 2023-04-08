package dferrero17.homework.api.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenWeatherData {
    private double latitude;
    private double longitude;

    @JsonProperty("coord")
    private void unpackCoord(HashMap<String, Double> coord) {
        this.latitude = coord.get("lat");
        this.longitude = coord.get("lon");
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
