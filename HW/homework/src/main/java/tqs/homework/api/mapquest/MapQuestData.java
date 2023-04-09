package tqs.homework.api.mapquest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MapQuestData {
    private String state;
    private String city;
    private String road;
    private String zip;
    private final Logger logger = LoggerFactory.getLogger(MapQuestData.class);

    @JsonProperty("results")
    private void unpackInfo(List<HashMap<String, Object>> results) {
        HashMap<String, Object> result = results.get(0);
        @SuppressWarnings("unchecked")
        HashMap<String, Object> location = (HashMap<String, Object>) (
                (List<Object>) result.get("locations")
        ).get(0);

        state = location.get("adminArea3").toString();
        city = location.get("adminArea5").toString();
        road = location.get("street").toString();
        zip = location.get("postalCode").toString();
        logger.info("MapQuest Unpacked");
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
