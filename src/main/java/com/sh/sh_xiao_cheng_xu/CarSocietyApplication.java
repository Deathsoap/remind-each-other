package com.sh.sh_xiao_cheng_xu;

import com.sh.sh_xiao_cheng_xu.config.WxMiniProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.sh.sh_xiao_cheng_xu.mapper")
public class CarSocietyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarSocietyApplication.class, args);
    }
}