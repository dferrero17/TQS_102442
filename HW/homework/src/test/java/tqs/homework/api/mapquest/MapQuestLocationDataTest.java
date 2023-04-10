package tqs.homework.api.mapquest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapQuestLocationDataTest {

    @Test
    void testParseResults() throws Exception {
        String json = "{" +
                "\"results\": [{" +
                "\"locations\": [{" +
                "\"latLng\": {" +
                "\"lat\": 40.123," +
                "\"lng\": -8.432" +
                "}" +
                "}]" +
                "}]" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        MapQuestLocationData mapQuestLocationData = objectMapper.readValue(json, MapQuestLocationData.class);

        assertEquals(40.123, mapQuestLocationData.getLat());
        assertEquals(-8.432, mapQuestLocationData.getLon());
    }
}
