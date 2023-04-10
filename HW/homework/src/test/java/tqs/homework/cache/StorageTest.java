package tqs.homework.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private Storage<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = Mockito.spy(new Storage<>(5000L));
    }

    @Test
    void testPutAndGet() {
        storage.put("key1", 1);
        assertEquals(1, storage.get("key1"));
    }

    @Test
    void testItemExpiration() {
        Mockito.doReturn(1000L).when(storage).getCurrentTimeMillis();

        storage.put("key2", 2);

        // Simulate time passing (6 seconds)
        Mockito.doReturn(1000L + 6000L).when(storage).getCurrentTimeMillis();

        assertNull(storage.get("key2"));
    }
}
