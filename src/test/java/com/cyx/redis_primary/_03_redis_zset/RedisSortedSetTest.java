package com.cyx.redis_primary._03_redis_zset;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RedisSortedSetTest {

    private HeroTop heroTop = new HeroTop();

    @Test
    @Disabled("模拟 TOP 榜")
    void testRedisSortedSet() {
        for (int i = 0; i < 50; i++) {
            heroTop.userHero();

            // 每使用 10 次英雄，进行一次查询
            if ((i + 1) % 10 == 0) {
                heroTop.getHeroTop();
            }
        }
    }
}
