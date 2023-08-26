package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * Spring Data Redis配置类
 */
@Configuration
@Slf4j
public class RedisConfig {
    //创建一个RedisTemplate对象
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis template对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器,使用字符串类型的key序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        return redisTemplate;
    }
}
