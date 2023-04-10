package tqs.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tqs.homework.api.mapquest.MapQuestData;
import tqs.homework.api.mapquest.MapQuestLocationData;
import tqs.homework.api.openweather.OpenWeatherComponent;
import tqs.homework.api.openweather.OpenWeatherData;
import tqs.homework.services.APIService;
import tqs.homework.services.GeoCodingService;
import tqs.homework.services.ReverseGeoCodingService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(APIController.class)
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private APIService aqService;

    @MockBean
    private GeoCodingService geoService;

    @MockBean
    private ReverseGeoCodingService reverseGeoCodingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAirQualityNow_success() throws Exception {
        OpenWeatherComponent mockComponent = new OpenWeatherComponent();
        when(aqService.getMostRecentAirQuality(any(Double.class), any(Double.class))).thenReturn(mockComponent);

        mockMvc.perform(get("/api/airquality/now?lat=10.0&lon=20.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getAirQualityForecast_success() throws Exception {
        OpenWeatherData mockData = new OpenWeatherData();
        when(aqService.getAirQuality(any(Double.class), any(Double.class))).thenReturn(mockData);

        mockMvc.perform(get("/api/airquality/history?lat=10.0&lon=20.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getCity_success() throws Exception {
        MapQuestData mockData = new MapQuestData();
        when(geoService.getGeoCoding(any(Double.class), any(Double.class))).thenReturn(mockData);

        mockMvc.perform(get("/api/city?lat=10.0&lon=20.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getReverseCity_success() throws Exception {
        String location = "New York";
        MapQuestLocationData mockData = new MapQuestLocationData();
        when(reverseGeoCodingService.getReverseGeoCoding(location)).thenReturn(mockData);

        mockMvc.perform(get("/api/city/{location}", location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(mockData));
    }

}
