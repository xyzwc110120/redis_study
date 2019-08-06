package com.cyx.redis_primary._02_redis_list;

import com.cyx.redis_primary.utils.JavaRedisClient;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {

    private Jedis jedis = JavaRedisClient.JAVA_REDIS_CLIENT.getJedis();

    // 实例化一个重入锁
    private final Lock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    private Random random = new Random();

    /**
     * 消费任务，从 Redis 中的 task 列表获取任务，并存入 temp 列表，
     * 如果消费成功，从 temp 列表中删除任务，如果消费失败，则将任务从 temp 列表弹回 task 列表，等待下次消费。
     */
    public void run() {
        // 若 Redis 中 task 列表和 temp 列表中还有任务，则接着消费
        while (jedis.llen("task") > 0 || jedis.llen("temp") > 0) {
            // 获取锁
            reentrantLock.lock();
            try {
                // 若 temp 列表中有任务
                if (jedis.llen("temp") > 0) {
                    // 从 Redis 中的 temp 列表尾部获得任务
                    String task = jedis.lindex("temp", -1);

                    // 设计消费失败
                    int bad = random.nextInt(5);
                    // 如果随机数为 2，则视为失败
                    if (bad == 2) {
                        // 将 temp 列表中失败的任务放回 task 列表顶部，等待下次消费
                        jedis.rpoplpush("temp", "task");
                        System.out.println("消费失败" + task);
                    } else {
                        // 如果消费成功，从 temp 列表中删除该任务
                        jedis.rpop("temp");
                        System.out.println("消费成功：" + task);
                    }
                } else {
                    // 若 temp 列表中没有任务，而 task 列表中有任务，则该线程睡眠 1 秒，等待 temp 列表从 task 列表取出任务
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
