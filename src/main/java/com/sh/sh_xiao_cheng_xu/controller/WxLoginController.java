package com.sh.sh_xiao_cheng_xu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
public class WxLoginController {

    // 模拟微信登录，真实环境需要调用微信官方接口换取openId，本地测试直接模拟
    @PostMapping("/login")
    public Map<String, Object> wxLogin(@RequestParam String code) {
        Map<String, Object> result = new HashMap<>();
        // 模拟获取openId，正式环境用code调用微信api
        String openId = "test_openid_123456";
        // 模拟用户ID，数据库查询/新增用户
        Long userId = 1L;

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("openId", openId);

        result.put("code", 200);
        result.put("data", data);
        result.put("msg", "登录成功");
        return result;
    }
}
