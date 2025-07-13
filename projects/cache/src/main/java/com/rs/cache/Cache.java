package com.rs.cache;

public interface Cache {
    void put(Integer key, Integer value);
    int get(Integer key);
}
