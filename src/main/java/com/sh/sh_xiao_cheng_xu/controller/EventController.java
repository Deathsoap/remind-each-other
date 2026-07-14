package com.sh.sh_xiao_cheng_xu.controller;

import com.sh.sh_xiao_cheng_xu.entity.SocietyEvent;
import com.sh.sh_xiao_cheng_xu.service.SocietyEventService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Resource
    private SocietyEventService eventService;

    @GetMapping("/list")
    public Map<String, Object> getEventList(@RequestParam Long societyId) {
        Map<String, Object> res = new HashMap<>();
        // 根据社团id查询数据库里该社团所有地点
        List<SocietyEvent> eventList = eventService.lambdaQuery()
                .eq(SocietyEvent::getSocietyId, societyId)
                .list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (SocietyEvent event : eventList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", event.getId());
            map.put("eventName", event.getEventName());
            result.add(map);
        }
        res.put("code", 200);
        res.put("data", result);
        return res;
    }
}