package tqs.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tqs.homework.api.openweather.OpenWeather;
import tqs.homework.api.openweather.OpenWeatherComponent;
import tqs.homework.api.openweather.OpenWeatherData;
import tqs.homework.statistics.Stats;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;


@SpringBootTest
class APIServiceTest {
    @Mock
    private OpenWeather openWeatherMock;
    @Mock(strictness = Mock.Strictness.LENIENT)
    private Stats statsMock;
    @InjectMocks
    private APIService apiService;

    @BeforeEach
    void setUp() {
        // openWeatherMock = Mockito.mock(OpenWeather.class);
        // statsMock = Mockito.mock(Stats.class);

        Mockito.mockStatic(Stats.class);
        when(Stats.getInstance()).thenReturn(statsMock);

        apiService = new APIService(openWeatherMock);
    }
    @AfterEach
    public void tearDown() {
        Mockito.framework().clearInlineMocks(); // Clear all inline mocks, including static mocks
    }

    @Test
    void getAirQuality_cacheMiss_apiCallSuccess() {
        OpenWeatherData mockData = new OpenWeatherData();
        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        OpenWeatherData result = apiService.getAirQuality(10.0, 20.0);

        assertEquals(mockData, result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(1)).incrementApiHits();
        verify(statsMock, times(0)).incrementCacheHits();
        verify(statsMock, times(0)).incrementApiFails();
    }

    @Test
    void getAirQuality_cacheMiss_apiCallFail() {
        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(null);

        OpenWeatherData result = apiService.getAirQuality(10.0, 20.0);

        assertNull(result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(0)).incrementApiHits();
        verify(statsMock, times(0)).incrementCacheHits();
        verify(statsMock, times(1)).incrementApiFails();
    }

    @Test
    void getAirQuality_cacheHit() {
        OpenWeatherData mockData = new OpenWeatherData();
        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        apiService.getAirQuality(10.0, 20.0); // First call (cache miss)
        OpenWeatherData result = apiService.getAirQuality(10.0, 20.0); // Second call (cache hit)

        assertEquals(mockData, result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(1)).incrementApiHits();
        verify(statsMock, times(1)).incrementCacheHits();
        verify(statsMock, times(0)).incrementApiFails();
    }

    @Test
    void getMostRecentAirQuality_apiCallSuccess() {
        OpenWeatherData mockData = new OpenWeatherData();
        OpenWeatherComponent component1 = new OpenWeatherComponent();
        component1.setDt(1000L);
        OpenWeatherComponent component2 = new OpenWeatherComponent();
        component2.setDt(2000L);
        mockData.setComponentList(Arrays.asList(component1, component2));

        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        OpenWeatherComponent result = apiService.getMostRecentAirQuality(10.0, 20.0);

        assertNotNull(result);
        assertEquals(component2, result);
    }

    @Test
    void getMostRecentAirQuality_apiCallFail() {
        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(null);

        OpenWeatherComponent result = apiService.getMostRecentAirQuality(10.0, 20.0);

        assertNull(result);
    }

    @Test
    void getMostRecentAirQuality_noComponents() {
        OpenWeatherData mockData = new OpenWeatherData();
        mockData.setComponentList(Collections.emptyList());
        when(openWeatherMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        OpenWeatherComponent result = apiService.getMostRecentAirQuality(10.0, 20.0);

        assertNull(result);
    }
}

