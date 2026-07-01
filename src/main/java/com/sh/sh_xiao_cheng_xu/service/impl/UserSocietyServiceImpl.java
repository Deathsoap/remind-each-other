package com.sh.sh_xiao_cheng_xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.sh_xiao_cheng_xu.entity.UserSociety;
import com.sh.sh_xiao_cheng_xu.mapper.UserSocietyMapper;
import com.sh.sh_xiao_cheng_xu.service.UserSocietyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserSocietyServiceImpl extends ServiceImpl<UserSocietyMapper, UserSociety> implements UserSocietyService {
    @Resource
    private UserSocietyMapper userSocietyMapper;

    @Override
    public String joinSociety(Long userId, Long societyId) {
        LambdaQueryWrapper<UserSociety> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSociety::getUserId, userId).eq(UserSociety::getSocietyId, societyId);
        UserSociety exist = this.getOne(wrapper);
        if (exist != null) {
            return "已加入该社团";
        }
        UserSociety us = new UserSociety();
        us.setUserId(userId);
        us.setSocietyId(societyId);
        us.setJoinTime(LocalDateTime.now());
        this.save(us);
        return "加入成功";
    }

    @Override
    public List<UserSociety> getByUserId(Long userId) {
        LambdaQueryWrapper<UserSociety> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSociety::getUserId, userId);
        return this.list(wrapper);
    }

    @Override
    public List<UserSociety> getBySocietyId(Long societyId) {
        LambdaQueryWrapper<UserSociety> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSociety::getSocietyId, societyId);
        return this.list(wrapper);
    }
}
