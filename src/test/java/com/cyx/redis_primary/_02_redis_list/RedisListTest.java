package com.cyx.redis_primary._02_redis_list;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
需求：
    生产者不断生产任务，放入 task-queue 排队，消费者不断拿出任务来处理，同时放入一个 temp-queue 暂存，
如果任务处理成功，则清除放入 temp-queue 中成功的任务，否则将任务重新弹回给 task-queue。
*/
class RedisListTest {

    @Test
    @Disabled("通过多线程来模拟需求")
    void testRedisList() throws InterruptedException {
        Producer producer = new Producer();
        Translator translator = new Translator();
        Consumer consumer = new Consumer();

        // 启动线程
        new Thread(producer).start();
        new Thread(translator).start();
        new Thread(consumer).start();

        // 为了避免因为主线成结束导致其他线程也结束，所以在这里睡眠 10 秒，让其他线程跑完
        Thread.sleep(10 * 1000);
    }
}