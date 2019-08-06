package com.cyx.redis_primary._04_redis_hash;

import com.cyx.redis_primary.utils.JavaRedisClient;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 模拟购物操操作
 */
public class ShoppingCart {

    private static final String PREFIX = "shopping_cart_";

    Jedis jedis = JavaRedisClient.JAVA_REDIS_CLIENT.getJedis();

    /**
     * 加入 / 修改购物车
     *
     * @param memberId 用户 id
     * @param product 商品名称
     * @param quantity 商品数量
     */
    public void addOrUpdateShoppingCart(Long memberId, String product, String quantity) {
        jedis.hset(PREFIX + memberId, product, quantity);
    }

    /**
     * 删除购物车
     *
     * @param memberId 用户 id
     * @param product 商品名称
     */
    public void delShoppingCart(Long memberId, String product) {
        jedis.hdel(PREFIX + memberId, product);
    }

    /**
     * 获取购物车列表
     *
     * @param memberId 用户 id
     */
    public void getShoppingCart(Long memberId) {
        Map<String, String> shoppingCart = jedis.hgetAll(PREFIX + memberId);
        for (Map.Entry product : shoppingCart.entrySet()) {
            System.out.println(product.getKey() + "：" + product.getValue());
        }
    }
}
