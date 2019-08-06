package com.cyx.redis_primary._02_redis_list;

import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {

    private Jedis jedis = new Jedis("192.168.106.110", 6379);

    // 实例化一个重入锁
    private final Lock reentrantLock = new ReentrantLock();

    /**
     * 生产任务，并存入 Redis 中的 task 列表
     */
    public void run() {
        for (int i = 0; i < 30; i++) {
            reentrantLock.lock();
            try {
                String task = "task" + i;
                jedis.lpush("task", task);
                System.out.println("生产任务：" + task);
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}