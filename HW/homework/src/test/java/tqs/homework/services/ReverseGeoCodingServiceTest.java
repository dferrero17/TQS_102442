package tqs.homework.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tqs.homework.api.mapquest.MapQuest;
import tqs.homework.api.mapquest.MapQuestLocationData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReverseGeoCodingServiceTest {

    private MapQuest mapQuestMock;
    private ReverseGeoCodingService reverseGeoCodingService;

    @BeforeEach
    void setUp() {
        mapQuestMock = Mockito.mock(MapQuest.class);
        reverseGeoCodingService = new ReverseGeoCodingService(mapQuestMock);
    }

    @AfterEach
    public void tearDown() {
        Mockito.framework().clearInlineMocks(); // Clear all inline mocks, including static mocks
    }

    @Test
    void getReverseGeoCoding_cacheMiss_apiCallSuccess() {
        MapQuestLocationData mockData = new MapQuestLocationData();
        when(mapQuestMock.reverseCall(any(String.class))).thenReturn(mockData);

        MapQuestLocationData result = reverseGeoCodingService.getReverseGeoCoding("123 Main St");

        assertEquals(mockData, result);
    }

    @Test
    void getReverseGeoCoding_cacheMiss_apiCallFail() {
        when(mapQuestMock.reverseCall(any(String.class))).thenReturn(null);

        MapQuestLocationData result = reverseGeoCodingService.getReverseGeoCoding("123 Main St");

        assertNull(result);
    }

    @Test
    void getReverseGeoCoding_cacheHit() {
        MapQuestLocationData mockData = new MapQuestLocationData();
        when(mapQuestMock.reverseCall(any(String.class))).thenReturn(mockData);

        reverseGeoCodingService.getReverseGeoCoding("123 Main St"); // First call (cache miss)
        MapQuestLocationData result = reverseGeoCodingService.getReverseGeoCoding("123 Main St"); // Second call (cache hit)

        assertEquals(mockData, result);
        verify(mapQuestMock, times(1)).reverseCall(any(String.class));
    }
}
