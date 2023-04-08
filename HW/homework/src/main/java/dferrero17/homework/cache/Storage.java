package dferrero17.homework.cache;

import java.util.HashMap;


public class Storage<Key, Value> {
    private final HashMap<Key, Value> data = new HashMap<>();
    private final HashMap<Key, Long> ttl = new HashMap<>();
    private final Long maxTtl;


    public Storage(Long maxTtl) {
        this.maxTtl = maxTtl;
    }

    public void put(Key key, Value value) {
        data.put(key, value);
        ttl.put(key, System.currentTimeMillis());
    }

    public Value get(Key key) {
        if (ttl.containsKey(key)) {
            if (System.currentTimeMillis() - ttl.get(key) > maxTtl) {
                data.remove(key);
                ttl.remove(key);
            }
        }
        return data.get(key);
    }
}
