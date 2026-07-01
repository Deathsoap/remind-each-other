package com.sh.sh_xiao_cheng_xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.sh_xiao_cheng_xu.entity.SocietyEvent;

import java.util.List;

public interface SocietyEventService extends IService<SocietyEvent> {
    SocietyEvent addEvent(Long societyId, String eventName);
    List<SocietyEvent> getEventBySociety(Long societyId);
}