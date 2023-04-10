package tqs.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.homework.HomeworkApplication;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = HomeworkApplication.class)
@AutoConfigureMockMvc
class ControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAirQualityNow_success() throws Exception {
        mockMvc.perform(get("/api/airquality/now?lat=10.0&lon=20.0"))
                .andExpect(status().isOk());
    }

    @Test
    void getAirQualityForecast_success() throws Exception {
        mockMvc.perform(get("/api/airquality/history?lat=10.0&lon=20.0"))
                .andExpect(status().isOk());
    }

    @Test
    void getCity_success() throws Exception {
        mockMvc.perform(get("/api/city?lat=10.0&lon=20.0"))
                .andExpect(status().isOk());
    }

    @Test
    void getReverseCity_success() throws Exception {
        String location = "New York";
        mockMvc.perform(get("/api/city/{location}", location))
                .andExpect(status().isOk());
    }
}
