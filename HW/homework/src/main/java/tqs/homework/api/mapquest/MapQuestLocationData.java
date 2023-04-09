package tqs.homework.api.mapquest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
import java.util.List;
import java.util.HashMap;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MapQuestLocationData {
    private double lat;
    private double lon;

    @JsonProperty("results")
    public void parseResults(List<Object> results) {
        Map<String, Object> result = (HashMap<String, Object>) results.get(0);
        Map<String, Object> locations = (HashMap<String, Object>) ((List<Object>) result.get("locations")).get(0);
        Map<String, Double> latlng = (HashMap<String, Double>) locations.get("latLng");
        lat = latlng.get("lat");
        lon = latlng.get("lng");
    }
}
