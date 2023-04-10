package tqs.homework.api.mapquest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapQuestDataTest {

    @Test
    void testUnpackInfo() throws Exception {
        String json = "{\"results\": [{\"locations\": [{" +
                "\"adminArea3\": \"State\"," +
                "\"adminArea5\": \"City\"," +
                "\"street\": \"Road\"," +
                "\"postalCode\": \"12345\"" +
                "}]}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        MapQuestData mapQuestData = objectMapper.readValue(json, MapQuestData.class);

        assertEquals("State", mapQuestData.getState());
        assertEquals("City", mapQuestData.getCity());
        assertEquals("Road", mapQuestData.getRoad());
        assertEquals("12345", mapQuestData.getZip());
    }
}
