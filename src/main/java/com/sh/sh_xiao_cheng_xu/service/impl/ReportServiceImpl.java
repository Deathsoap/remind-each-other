package com.sh.sh_xiao_cheng_xu.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.sh_xiao_cheng_xu.entity.ReportInfo;
import com.sh.sh_xiao_cheng_xu.entity.Society;
import com.sh.sh_xiao_cheng_xu.entity.SocietyEvent;
import com.sh.sh_xiao_cheng_xu.mapper.ReportInfoMapper;
import com.sh.sh_xiao_cheng_xu.service.ReportService;
import com.sh.sh_xiao_cheng_xu.service.SocietyEventService;
import com.sh.sh_xiao_cheng_xu.service.SocietyService;
import com.sh.sh_xiao_cheng_xu.websocket.WebSocketServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportInfoMapper, ReportInfo> implements ReportService {
    @Resource
    private ReportInfoMapper reportMapper;
    @Resource
    private SocietyService societyService;
    @Resource
    private SocietyEventService eventService;

    @Override
    public String publishReport(Long userId, Integer societyId, Long eventId, String desc, String imgUrls) {
        // 1.查询社团后缀、地点名称
        Society society = societyService.getById(societyId);
        SocietyEvent event = eventService.getById(eventId);
        String fullMsg = "【" + event.getEventName() + "】" + desc + society.getMsgSuffix();

        // 2.保存记录
        ReportInfo report = new ReportInfo();
        report.setSocietyId(societyId);
        report.setEventId(eventId);
        report.setPublisherUid(userId);
        report.setDescription(desc);
        report.setImgUrl(imgUrls);
        report.setCreateTime(LocalDateTime.now());
        this.save(report);

        // 3.封装推送JSON给小程序（带文本+图片）
        Map<String, Object> pushData = new HashMap<>();
        pushData.put("msg", fullMsg);
        pushData.put("imgUrl", imgUrls);
        pushData.put("time", LocalDateTime.now().toString());
        String json = JSON.toJSONString(pushData);

        // 4.推送社团全部在线用户
        WebSocketServer.sendSocietyMsg(societyId, json);
        return fullMsg;
    }

    @Override
    public List<ReportInfo> getHistoryBySociety(Long societyId) {
        LambdaQueryWrapper<ReportInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReportInfo::getSocietyId, societyId);
        wrapper.orderByDesc(ReportInfo::getCreateTime);
        return this.list(wrapper);
    }
}
