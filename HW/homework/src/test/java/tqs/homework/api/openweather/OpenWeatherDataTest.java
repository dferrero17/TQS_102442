package tqs.homework.api.openweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenWeatherDataTest {

    @Test
    void testUnpackCoord() throws Exception {
        String json = "{\"coord\": {\"lat\": 12.34, \"lon\": 56.78}}";
        ObjectMapper objectMapper = new ObjectMapper();
        OpenWeatherData openWeatherData = objectMapper.readValue(json, OpenWeatherData.class);

        assertEquals(12.34, openWeatherData.getLatitude());
        assertEquals(56.78, openWeatherData.getLongitude());
    }
}
