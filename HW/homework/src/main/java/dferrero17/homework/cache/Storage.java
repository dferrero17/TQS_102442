package dferrero17.homework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class Storage<Key, Value> {
    private final HashMap<Key, Value> data = new HashMap<>();
    private final HashMap<Key, Long> ttl = new HashMap<>();
    private final Long maxTtl;

    private final Logger logger = LoggerFactory.getLogger(Storage.class);

    public Storage(Long maxTtl) {
        this.maxTtl = maxTtl;
    }

    public void put(Key key, Value value) {
        data.put(key, value);
        ttl.put(key, System.currentTimeMillis());
        logger.info("Storage put");
    }

    public Value get(Key key) {
        if (ttl.containsKey(key)) {
            if (System.currentTimeMillis() - ttl.get(key) > maxTtl) {
                data.remove(key);
                ttl.remove(key);
                logger.info("Storage removed");
            }
        }
        logger.info("Storage get");
        return data.get(key);
    }
}
