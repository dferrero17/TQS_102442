package dferrero17.homework.api.mapquest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MapQuestData {
    private String state;
    private String city;
    private String road;
    private String zip;

    @JsonProperty("results")
    private void unpackInfo(List<HashMap<String, Object>> results) {
        HashMap<String, Object> result = results.get(0);

        HashMap<String, Object> location = (HashMap<String, Object>) (
                (List<Object>) result.get("locations")
        ).get(0);

        state = location.get("adminArea3").toString();
        city = location.get("adminArea5").toString();
        road = location.get("street").toString();
        zip = location.get("postalCode").toString();
    }

    @Override
    public String toString() {
        return "MapQuestData{" +
                "state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", road='" + road + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
