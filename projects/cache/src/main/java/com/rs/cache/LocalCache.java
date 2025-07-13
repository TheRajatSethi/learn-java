package com.rs.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache implements Cache{
    int maxSize;
    Map<Integer, Integer> data = new ConcurrentHashMap<>();

    LocalCache(int maxSize){
        this.maxSize = maxSize;
    }

    @Override
    public void put(Integer key, Integer value) {
        if (data.size() < maxSize) // TODO --> Should be refactored to remove data based on cache policy.
            data.put(key, value);
    }

    @Override
    public int get(Integer key) {
        return 0;
    }
}
