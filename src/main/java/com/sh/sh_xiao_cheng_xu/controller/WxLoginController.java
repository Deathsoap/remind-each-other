package com.sh.sh_xiao_cheng_xu.controller;

import com.alibaba.fastjson2.JSONObject;
import com.sh.sh_xiao_cheng_xu.entity.WxUser;
import com.sh.sh_xiao_cheng_xu.mapper.WxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
public class WxLoginController {

    @Value("${wx.miniapp.appid:wxdf61837a068247c3}")
    private String appId;

    @Value("${wx.miniapp.secret:47ed23242448cd909ac2213a52455e5f}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxUserMapper wxUserMapper;

    @PostMapping("/login")
// 新增avatarUrl、nickName接收前端传过来的用户信息
    public Map<String, Object> wxLogin(
            @RequestParam String code,
            @RequestParam(required = false) String avatarUrl,
            @RequestParam(required = false) String nickName
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId
                    + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.parseObject(response);
            String openId = json.getString("openid");

            if (openId == null || openId.isEmpty()) {
                result.put("code", 500);
                result.put("msg", "获取openId失败");
                return result;
            }

            WxUser user = wxUserMapper.selectByOpenId(openId);
            if (user == null) {
                WxUser newUser = new WxUser();
                newUser.setOpenId(openId);
                newUser.setAvatarUrl(avatarUrl);
                newUser.setNickName(nickName);
                wxUserMapper.insert(newUser);
                user = wxUserMapper.selectByOpenId(openId);
            } else {
                // 老用户更新头像昵称
                user.setAvatarUrl(avatarUrl);
                user.setNickName(nickName);
                wxUserMapper.updateById(user);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("openId", openId);
            data.put("nickName", user.getNickName());
            data.put("avatarUrl", user.getAvatarUrl());

            result.put("code", 200);
            result.put("data", data);
            result.put("msg", "登录成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "登录异常：" + e.getMessage());
        }
        return result;
    }
}