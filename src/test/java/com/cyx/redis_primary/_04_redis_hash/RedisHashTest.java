package com.cyx.redis_primary._04_redis_hash;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RedisHashTest {

    private ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    @Disabled("使用 Redis 中的 hash 数据结构模拟购物车")
    void testRedisHash() {
        Long memberId = 1L;
        shoppingCart.addOrUpdateShoppingCart(memberId, "哇哈哈", "3");
        shoppingCart.addOrUpdateShoppingCart(memberId, "可口可乐", "2");
        shoppingCart.addOrUpdateShoppingCart(memberId, "旺仔牛奶", "4");

        shoppingCart.getShoppingCart(memberId);

        shoppingCart.delShoppingCart(memberId, "旺仔牛奶");
        shoppingCart.getShoppingCart(memberId);
    }
}
