package com.cyx.redis_primary._02_redis_list;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Translator implements Runnable {

    private Jedis jedis = new Jedis("192.168.106.110", 6379);

    // 实例化一个重入锁
    private final Lock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    /**
     * 将 Redis 中 task 列表中的任务存入 temp 列表
     */
    public void run() {
        while (jedis.llen("task") > 0 || jedis.llen("temp") > 0) {
            // 获取锁
            reentrantLock.lock();
            try {
                // 若 task 列表中有任务
                if (jedis.llen("task") > 0) {
                    System.out.println("取出任务：" + jedis.lindex("task", -1));
                    jedis.rpoplpush("task", "temp");
                } else {
                    // 若 task 列表中没有任务，而 temp 列表中还有任务，则使线程睡眠 1 秒，看会不会消费失败需要重新从 task 列表取出
                    condition.await(1L, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                reentrantLock.unlock();
            }
        }
    }
}
