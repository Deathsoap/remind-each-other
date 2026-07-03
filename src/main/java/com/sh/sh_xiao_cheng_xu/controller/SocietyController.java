package com.sh.sh_xiao_cheng_xu.controller;

import com.sh.sh_xiao_cheng_xu.entity.Society;
import com.sh.sh_xiao_cheng_xu.entity.SocietyEvent;
import com.sh.sh_xiao_cheng_xu.entity.UserSociety;
import com.sh.sh_xiao_cheng_xu.service.SocietyEventService;
import com.sh.sh_xiao_cheng_xu.service.SocietyService;
import com.sh.sh_xiao_cheng_xu.service.UserSocietyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/society")
public class SocietyController {

    @Resource
    private SocietyService societyService;

    @Resource
    private SocietyEventService eventService;

    @Resource
    private UserSocietyService userSocietyService;

    // 创建社团
    @PostMapping("/create")
    public Map<String, Object> createGroup(
            @RequestParam Long userId,
            @RequestParam String groupName,
            @RequestParam String msgSuffix,
            @RequestParam String eventNames // 前端逗号拼接的地点字符串
    ) {
        Map<String, Object> res = new HashMap<>();
        // 1.保存社团
        Society society = new Society();
        society.setGroupName(groupName);
        society.setMsgSuffix(msgSuffix);
        society.setAdminUserId(userId);
        societyService.save(society);
        Integer newSocietyId = Math.toIntExact(society.getId());

        // 2.自动把创建人加入用户社团关联表
        UserSociety us = new UserSociety();
        us.setUserId(userId);
        us.setSocietyId(Long.valueOf(newSocietyId));
        userSocietyService.save(us);

        // 3.拆分地点字符串，批量插入 society_event 地点表
        String[] nameArr = eventNames.split(",");
        List<SocietyEvent> eventList = new ArrayList<>();
        for(String name : nameArr){
            SocietyEvent event = new SocietyEvent();
            event.setSocietyId(Long.valueOf(newSocietyId));
            event.setEventName(name);
            eventList.add(event);
        }
        eventService.saveBatch(eventList);

        res.put("code", 200);
        res.put("msg", "创建成功");
        return res;
    }

    // 查询所有社团
    @GetMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        // 模拟社团数据
        Map<String, Object> group1 = new HashMap<>();
        group1.put("id", 1);
        group1.put("groupName", "车友一队");
        group1.put("msgSuffix", "前方检查，减速避让");
        list.add(group1);

        Map<String, Object> group2 = new HashMap<>();
        group2.put("id", 2);
        group2.put("groupName", "同城车友群");
        group2.put("msgSuffix", "拥堵路段，注意慢行");
        list.add(group2);

        res.put("code", 200);
        res.put("data", list);
        return res;
    }

    // 用户加入社团
    @PostMapping("/join")
    public Map<String, Object> joinGroup(@RequestParam Long userId, @RequestParam Long societyId) {
        Map<String, Object> res = new HashMap<>();
        // 数据库关联用户-社团
        res.put("code", 200);
        res.put("data", "加入成功");
        return res;
    }

    // 查询我加入的社团
    @GetMapping("/myJoin")
    public Map<String, Object> myJoin(@RequestParam Long userId) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        // 模拟当前用户已加入车友一队
        Map<String, Object> group = new HashMap<>();
        group.put("societyId", 1);
        group.put("groupName", "车友一队");
        list.add(group);

        res.put("code", 200);
        res.put("data", list);
        return res;
    }
}
