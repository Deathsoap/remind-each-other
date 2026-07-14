package com.sh.sh_xiao_cheng_xu.controller;

import com.sh.sh_xiao_cheng_xu.entity.ReportInfo;
import com.sh.sh_xiao_cheng_xu.entity.Society;
import com.sh.sh_xiao_cheng_xu.entity.WxUser;
import com.sh.sh_xiao_cheng_xu.service.ReportService;
import com.sh.sh_xiao_cheng_xu.service.SocietyService;
import com.sh.sh_xiao_cheng_xu.service.UserSocietyService;
import com.sh.sh_xiao_cheng_xu.service.WxUserService;
import com.sh.sh_xiao_cheng_xu.util.WxSubscribeUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    // 只注入service，不再注入WebSocket
    @Resource
    private ReportService reportService;

    @Resource
    private UserSocietyService userSocietyService;

    @Resource
    private SocietyService societyService;

    @Resource
    private WxUserService wxUserService;

    @PostMapping("/publish")
    public Map<String, Object> publishReport(
            @RequestParam Long userId,
            @RequestParam Long societyId,
            @RequestParam Long eventId,
            @RequestParam String description,
            @RequestParam String imgUrls
    ) {
        Map<String, Object> res = new HashMap<>();
        // 推送逻辑全部丢给service处理，控制器只做接收参数
        String pushMsg = reportService.publishReport(userId, societyId, eventId, description, imgUrls);
        res.put("code", 200);
        res.put("data", pushMsg);
        return res;
    }

    @PostMapping("/submit")
    public Map<String, Object> submitReport(@RequestBody ReportInfo report) {
        Map<String, Object> result = new HashMap<>();
        // 1、保存上报记录
        reportService.save(report);

        // 2、MP方法获取社团全部成员openId
        List<String> openIdList = userSocietyService.getMemberOpenIdBySocietyId(report.getSocietyId());
        if(openIdList.isEmpty()){
            result.put("code", 200);
            result.put("msg", "发布成功，暂无社团其他成员");
            return result;
        }

        // 3、获取社团名称
        Society society = societyService.getById(report.getSocietyId());
        String groupName = society.getGroupName();

        // 4、获取发布者openId，推送时跳过自己
        WxUser publisher = wxUserService.getById(report.getPublisherUid());
        String publisherOpenId = publisher.getOpenId();

        // 5、循环推送订阅消息（免费微信官方通知）
        for (String openId : openIdList) {
            // 不给发布者自己推送
            if (!openId.equals(publisherOpenId)) {
                WxSubscribeUtil.sendMsg(openId, groupName, report.getDescription(), report.getImgUrl());
            }
        }

        result.put("code", 200);
        result.put("msg", "发布成功，已推送通知给社团其他成员");
        return result;
    }


}