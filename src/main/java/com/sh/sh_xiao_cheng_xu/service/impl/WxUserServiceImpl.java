package com.sh.sh_xiao_cheng_xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.sh_xiao_cheng_xu.entity.WxUser;
import com.sh.sh_xiao_cheng_xu.mapper.WxUserMapper;
import com.sh.sh_xiao_cheng_xu.service.WxUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
    @Resource
    private WxUserMapper wxUserMapper;

    @Override
    public WxUser getByOpenId(String openId) {
        LambdaQueryWrapper<WxUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WxUser::getOpenId, openId);
        return this.getOne(wrapper);
    }

    @Override
    public WxUser createUser(String openId) {
        WxUser user = new WxUser();
        user.setOpenId(openId);
        user.setNickName("");
        user.setAvatarUrl("");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return getByOpenId(openId);
    }
}