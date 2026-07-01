package com.sh.sh_xiao_cheng_xu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.sh_xiao_cheng_xu.entity.SocietyEvent;
import com.sh.sh_xiao_cheng_xu.mapper.SocietyEventMapper;
import com.sh.sh_xiao_cheng_xu.service.SocietyEventService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocietyEventServiceImpl extends ServiceImpl<SocietyEventMapper, SocietyEvent> implements SocietyEventService {
    @Resource
    private SocietyEventMapper eventMapper;

    @Override
    public SocietyEvent addEvent(Long societyId, String eventName) {
        SocietyEvent e = new SocietyEvent();
        e.setSocietyId(societyId);
        e.setEventName(eventName);
        e.setCreateTime(LocalDateTime.now());
        this.save(e);
        return e;
    }

    @Override
    public List<SocietyEvent> getEventBySociety(Long societyId) {
        LambdaQueryWrapper<SocietyEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SocietyEvent::getSocietyId, societyId);
        return this.list(wrapper);
    }
}
