package com.sh.sh_xiao_cheng_xu.controller;

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

    @GetMapping("/list")
    public Map<String, Object> getEventList(@RequestParam Long societyId) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> e1 = new HashMap<>();
        e1.put("id", 1);
        e1.put("eventName", "高速收费站");
        list.add(e1);

        Map<String, Object> e2 = new HashMap<>();
        e2.put("id", 2);
        e2.put("eventName", "城区十字路口");
        list.add(e2);

        res.put("code", 200);
        res.put("data", list);
        return res;
    }
}