package com.cyx.redis_primary.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
public class Member implements Serializable {

    private String name;
    private Integer age;
    private String sex;

    // 使用 Jackson 反序列化需要无参构造函数
    public Member() {
    }

    public Member(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
