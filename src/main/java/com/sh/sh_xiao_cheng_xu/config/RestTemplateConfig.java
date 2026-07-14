package com.sh.sh_xiao_cheng_xu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 网络请求工具配置
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 注册RestTemplate Bean，全局可@Autowired注入
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}