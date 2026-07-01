package com.sh.sh_xiao_cheng_xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.sh_xiao_cheng_xu.entity.Society;

import java.util.List;

public interface SocietyService extends IService<Society> {
    Society createSociety(Long adminUid, String groupName, String msgSuffix);
    List<Society> getAllSociety();
    List<Society> getMyCreateSociety(Long adminUid);
}
