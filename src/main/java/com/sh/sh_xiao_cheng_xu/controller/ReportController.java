package com.sh.sh_xiao_cheng_xu.controller;

import com.sh.sh_xiao_cheng_xu.service.ReportService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    // 只注入service，不再注入WebSocket
    @Resource
    private ReportService reportService;

    @PostMapping("/publish")
    public Map<String, Object> publishReport(
            @RequestParam Long userId,
            @RequestParam Integer societyId,
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
}