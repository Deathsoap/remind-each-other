package com.sh.sh_xiao_cheng_xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.sh_xiao_cheng_xu.entity.UserSociety;

import java.util.List;

public interface UserSocietyService extends IService<UserSociety> {
    String joinSociety(Long userId, Long societyId);
    List<UserSociety> getByUserId(Long userId);
    List<UserSociety> getBySocietyId(Long societyId);
}
