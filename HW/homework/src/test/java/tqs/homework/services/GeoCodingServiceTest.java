package tqs.homework.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tqs.homework.api.mapquest.MapQuest;
import tqs.homework.api.mapquest.MapQuestData;
import tqs.homework.statistics.Stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GeoCodingServiceTest {

    private MapQuest mapQuestMock;
    private Stats statsMock;
    private GeoCodingService geoCodingService;

    @BeforeEach
    void setUp() {
        mapQuestMock = Mockito.mock(MapQuest.class);
        statsMock = Mockito.mock(Stats.class);

        Mockito.mockStatic(Stats.class);
        when(Stats.getInstance()).thenReturn(statsMock);

        geoCodingService = new GeoCodingService(mapQuestMock);
    }

    @AfterEach
    public void tearDown() {
        Mockito.framework().clearInlineMocks(); // Clear all inline mocks, including static mocks
    }

    @Test
    void getGeoCoding_cacheMiss_apiCallSuccess() {
        MapQuestData mockData = new MapQuestData();
        when(mapQuestMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        MapQuestData result = geoCodingService.getGeoCoding(10.0, 20.0);

        assertEquals(mockData, result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(1)).incrementApiHits();
        verify(statsMock, times(0)).incrementCacheHits();
        verify(statsMock, times(0)).incrementApiFails();
    }

    @Test
    void getGeoCoding_cacheMiss_apiCallFail() {
        when(mapQuestMock.call(any(Double.class), any(Double.class))).thenReturn(null);

        MapQuestData result = geoCodingService.getGeoCoding(10.0, 20.0);

        assertNull(result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(0)).incrementApiHits();
        verify(statsMock, times(0)).incrementCacheHits();
        verify(statsMock, times(1)).incrementApiFails();
    }

    @Test
    void getGeoCoding_cacheHit() {
        MapQuestData mockData = new MapQuestData();
        when(mapQuestMock.call(any(Double.class), any(Double.class))).thenReturn(mockData);

        geoCodingService.getGeoCoding(10.0, 20.0); // First call (cache miss)
        MapQuestData result = geoCodingService.getGeoCoding(10.0, 20.0); // Second call (cache hit)

        assertEquals(mockData, result);
        verify(statsMock, times(1)).incrementCacheMisses();
        verify(statsMock, times(1)).incrementApiHits();
        verify(statsMock, times(1)).incrementCacheHits();
        verify(statsMock, times(0)).incrementApiFails();
    }
}