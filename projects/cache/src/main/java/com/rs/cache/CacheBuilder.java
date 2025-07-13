package com.rs.cache;


public class CacheBuilder {
    // Default Config
    private int maxSize = 10;

    // Need a private constructor as the builder should not be instanced.
    private CacheBuilder(){}

    public static CacheBuilder newBuilder(){
        return new CacheBuilder();
    }

    public CacheBuilder maxSize(int size){
        this.maxSize = size;
        return this;
    }

    public Cache build(){
        return new LocalCache(this.maxSize);
    }
}
