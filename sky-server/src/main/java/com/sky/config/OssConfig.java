package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类,用于创建OSS工具对象
 */
@Configuration
@Slf4j
public class OssConfig {
    //通过形参注入
    @Bean
    @ConditionalOnMissingBean  //条件对象,没有时才创建
    public AliOssUtil aliOssUtil(AliOssProperties properties) {
        log.info("创建阿里云OSS工具类对象");
        return new AliOssUtil(properties.getEndpoint(), properties.getAccessKeyId(),
                properties.getAccessKeySecret(), properties.getBucketName());
    }
}
