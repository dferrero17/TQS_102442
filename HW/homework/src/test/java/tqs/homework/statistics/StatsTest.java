package tqs.homework.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatsTest {
    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = Stats.getInstance();
        stats.reset(); // Reset stats before each test
    }

    @Test
    void testSingletonInstance() {
        assertNotNull(stats);
        assertEquals(stats, Stats.getInstance());
    }

    @Test
    void testIncrementMethods() {
        stats.incrementCacheMisses();
        stats.incrementCacheHits();
        stats.incrementApiFails();
        stats.incrementApiHits();

        Map<String, Integer> map = stats.toHashMap();
        assertEquals(1, map.get("cacheMisses"));
        assertEquals(1, map.get("cacheHits"));
        assertEquals(1, map.get("apiFails"));
        assertEquals(1, map.get("apiHits"));
    }

    @Test
    void testReset() {
        stats.incrementCacheMisses();
        stats.incrementCacheHits();
        stats.incrementApiFails();
        stats.incrementApiHits();
        stats.reset();

        Map<String, Integer> map = stats.toHashMap();
        assertEquals(0, map.get("cacheMisses"));
        assertEquals(0, map.get("cacheHits"));
        assertEquals(0, map.get("apiFails"));
        assertEquals(0, map.get("apiHits"));
    }

    @Test
    void testToHashMap() {
        stats.incrementCacheMisses();
        stats.incrementCacheHits();

        Map<String, Integer> map = stats.toHashMap();
        assertEquals(1, map.get("cacheMisses"));
        assertEquals(1, map.get("cacheHits"));
        assertEquals(0, map.get("apiFails"));
        assertEquals(0, map.get("apiHits"));
    }
}
