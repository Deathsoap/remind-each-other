package com.sh.sh_xiao_cheng_xu.util;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class WxSubscribeUtil {
    // 替换成你小程序的appid、secret
    private static final String APP_ID = "你的小程序AppID";
    private static final String APP_SECRET = "你的小程序AppSecret";
    // 你后台申请的订阅消息模板ID
    private static final String TEMPLATE_ID = "你的订阅消息模板ID";

    private static final RestTemplate restTemplate = new RestTemplate();

    // 获取微信access_token（免费接口，2小时刷新一次）
    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + APP_SECRET;
        String resp = restTemplate.getForObject(url, String.class);
        Map<String, Object> map = JSON.parseObject(resp, Map.class);
        return (String) map.get("access_token");
    }

    // 推送订阅消息给单个用户
    public static void sendMsg(String openId, String societyName, String desc, String imgUrl) {
        String accessToken = getAccessToken();
        String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;

        Map<String, Object> param = new HashMap<>();
        param.put("touser", openId);
        param.put("template_id", TEMPLATE_ID);
        // 跳转小程序页面（上报列表页）
        param.put("page", "pages/index/index");

        // 模板参数，和你后台模板字段一一对应
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> s1 = new HashMap<>();
        s1.put("value", societyName);
        data.put("thing1", s1);

        Map<String, String> s2 = new HashMap<>();
        s2.put("value", desc);
        data.put("thing2", s2);
        param.put("data", data);

        restTemplate.postForObject(sendUrl, param, String.class);
    }
}