package com.cyx.redis_primary.utils;

import redis.clients.jedis.Jedis;

/**
 * 使用单例模式，获取 Jedis 实例
 */
public enum JavaRedisClient {

    JAVA_REDIS_CLIENT;

    private Jedis jedis = new Jedis("192.168.106.110", 6379);

    public Jedis getJedis() {
        return jedis;
    }
}
