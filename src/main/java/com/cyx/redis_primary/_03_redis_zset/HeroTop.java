package com.cyx.redis_primary._03_redis_zset;

import com.cyx.redis_primary.utils.JavaRedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Random;
import java.util.Set;

public class HeroTop {

    private Jedis jedis = JavaRedisClient.JAVA_REDIS_CLIENT.getJedis();

    private Random random = new Random();

    private static final String[] heroes = {"卡特", "阿卡丽", "阿狸", "薇恩", "菲奥娜"};

    /**
     * 使用英雄
     */
    public void userHero() {
        // 随机使用一个英雄
        int index = random.nextInt(5);
        // 向 Redis 中的有序集合 hero_top 添加英雄及使用次数（分数）
        jedis.zincrby("hero_top", 1, heroes[index]);
    }

    /**
     * 获取英雄使用榜
     */
    public void getHeroTop() {
        // 按逆序（从大到小）获取 hero_top 集合数据
        Set<Tuple> hero_top = jedis.zrevrangeWithScores("hero_top", 0, -1);
        for (Tuple hero : hero_top) {
            System.out.println(hero.getElement() + "：" + hero.getScore());
        }
        System.out.println("-----------------------------------------");
    }
}
