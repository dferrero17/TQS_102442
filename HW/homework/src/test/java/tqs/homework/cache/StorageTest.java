package tqs.homework.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private Storage<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new Storage<>(5000L);
    }

    @Test
    void testPutAndGet() {
        storage.put("key1", 1);
        assertEquals(1, storage.get("key1"));
    }

    @Test
    void testItemExpiration() throws InterruptedException {
        storage.put("key2", 2);
        Thread.sleep(6000L); // sleep for 6 seconds, longer than maxTtl
        assertNull(storage.get("key2"));
    }
}
