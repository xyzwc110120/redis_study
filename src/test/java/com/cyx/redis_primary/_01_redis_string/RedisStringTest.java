package com.cyx.redis_primary._01_redis_string;

import com.cyx.redis_primary.entities.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RedisStringTest {

    private RedisString redisString = new RedisString();

    @Test
    @Disabled("测试通过 Redis Java 客户端操作 String 数据类型数据")
    void testRedisString() throws Exception {
        Member member = new Member("哇哈哈", 19, "男");

        redisString.redisStringBySerializable(member);
        redisString.redisStringByJson(member);
    }
}