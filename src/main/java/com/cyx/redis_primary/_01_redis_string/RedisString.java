package com.cyx.redis_primary._01_redis_string;

import com.cyx.redis_primary.entities.Member;
import com.cyx.redis_primary.utils.JavaRedisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RedisString {

    // 获取 Java Redis 客户端实例
    private Jedis jedis = JavaRedisClient.JAVA_REDIS_CLIENT.getJedis();

    /**
     * 通过 Java 序列化来向 Redis 保存对象
     */
    public void redisStringBySerializable(Member member) throws Exception {
        String key = "member1";

        // 将对象序列化成字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // 用对象序列化流来将 Member 对象序列化，然后把序列化之后二进制数据写到 byteArrayOutputStream 流中
        objectOutputStream.writeObject(member);
        // 将 byteArrayOutputStream 流转化成 byte 数组
        byte[] inMemberBytes = byteArrayOutputStream.toByteArray();

        // 将 Member 对象序列化之后的 byte 数组存到 Redis 的 String 结构数据中
        // 参数设置对象
        SetParams setParams = new SetParams();
        // 设置过期时间：30 秒
        setParams.ex(30);
        jedis.set(key.getBytes(), inMemberBytes, setParams);

        // 根据 key 从 Redis 中取出对象的 byte 数据
        byte[] outMemberByte = jedis.get(key.getBytes());
        // 将 byte 数据反序列出对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outMemberByte);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        // 从对象读取流中读取 Member 对象
        Member outMember = (Member) objectInputStream.readObject();

        // 打印对象
        System.out.println(outMember);
    }


    /**
     * 通过 json 来向 Redis 保存对象
     */
    public void redisStringByJson(Member member) throws Exception {
        String key = "member2";

        // 利用 Jackson 将对象转化成 json 串
        ObjectMapper objectMapper = new ObjectMapper();
        String memberJson = objectMapper.writeValueAsString(member);

        // 将 json 串存入 Redis
        SetParams setParams = new SetParams();
        setParams.ex(30);
        jedis.set(key, memberJson, setParams);

        // 从 Redis 中取出 Member 对象的 json 串
        memberJson = jedis.get(key);
        Member outMember = objectMapper.readValue(memberJson, Member.class);

        System.out.println(outMember);
    }
}
