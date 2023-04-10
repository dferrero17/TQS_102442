package tqs.homework.api.openweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenWeatherComponentTest {

    @Test
    void testUnpackLogic() throws Exception {
        String json = "{" +
                "\"dt\": 1629208500," +
                "\"main\": {\"aqi\": 2}," +
                "\"components\": {" +
                "\"co\": 215.55," +
                "\"no\": 0.12," +
                "\"no2\": 0.23," +
                "\"o3\": 63.57," +
                "\"so2\": 0.58," +
                "\"pm2_5\": 1.23," +
                "\"pm10\": 2.34," +
                "\"nh3\": 0.49" +
                "}" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        OpenWeatherComponent openWeatherComponent = objectMapper.readValue(json, OpenWeatherComponent.class);

        assertEquals(1629208500000L, openWeatherComponent.getDt());
        assertEquals(2, openWeatherComponent.getAqi());
        assertEquals(215.55, openWeatherComponent.getCo());
        assertEquals(0.12, openWeatherComponent.getNo());
        assertEquals(0.23, openWeatherComponent.getNo2());
        assertEquals(63.57, openWeatherComponent.getO3());
        assertEquals(0.58, openWeatherComponent.getSo2());
        assertEquals(1.23, openWeatherComponent.getPm2_5());
        assertEquals(2.34, openWeatherComponent.getPm10());
        assertEquals(0.49, openWeatherComponent.getNh3());
    }
}
