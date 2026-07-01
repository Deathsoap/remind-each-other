package com.sh.sh_xiao_cheng_xu.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMiniConfig {
    @Value("${wx.miniapp.app-id}")
    private String appId;
    @Value("${wx.miniapp.app-secret}")
    private String appSecret;

    @Bean
    public WxMaService wxMaService(){
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appId);
        config.setSecret(appSecret);
        cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl service = new cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
