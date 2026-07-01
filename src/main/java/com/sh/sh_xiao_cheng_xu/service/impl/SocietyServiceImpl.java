package com.sh.sh_xiao_cheng_xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.sh_xiao_cheng_xu.entity.Society;
import com.sh.sh_xiao_cheng_xu.mapper.SocietyMapper;
import com.sh.sh_xiao_cheng_xu.service.SocietyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocietyServiceImpl extends ServiceImpl<SocietyMapper, Society> implements SocietyService {
    @Resource
    private SocietyMapper societyMapper;

    @Override
    public Society createSociety(Long adminUid, String groupName, String msgSuffix) {
        Society s = new Society();
        s.setGroupName(groupName);
        s.setMsgSuffix(msgSuffix);
        s.setAdminUserId(adminUid);
        s.setCreateTime(LocalDateTime.now());
        s.setUpdateTime(LocalDateTime.now());
        this.save(s);
        return s;
    }

    @Override
    public List<Society> getAllSociety() {
        return this.list();
    }

    @Override
    public List<Society> getMyCreateSociety(Long adminUid) {
        LambdaQueryWrapper<Society> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Society::getAdminUserId, adminUid);
        return this.list(wrapper);
    }
}