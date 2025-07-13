package com.rs;

import com.rs.cache.CacheBuilder;

public class Usage {
    public static void main(String[] args) {
        var c = CacheBuilder.newBuilder().maxSize(8).build();
        c.put(10, 20);
        System.out.println(c.get(10));
    }
}