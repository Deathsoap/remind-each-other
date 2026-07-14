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
        // 查询数据库全部社团
        List<Society> allSocietyList = societyService.list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Society item : allSocietyList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("groupName", item.getGroupName());
            map.put("msgSuffix", item.getMsgSuffix());
            result.add(map);
        }
        res.put("code", 200);
        res.put("data", result);
        return res;
    }

    // 用户加入社团
    // 用户加入社团
    @PostMapping("/join")
    public Map<String, Object> joinGroup(@RequestParam Long userId, @RequestParam Long societyId) {
        Map<String, Object> res = new HashMap<>();

        // 1. 判断社团是否存在
        Society society = societyService.getById(societyId);
        if (society == null) {
            res.put("code", 500);
            res.put("msg", "社团不存在");
            return res;
        }

        // 2. 判断用户是否已经加入该社团，避免重复插入
        long count = userSocietyService.lambdaQuery()
                .eq(UserSociety::getUserId, userId)
                .eq(UserSociety::getSocietyId, societyId)
                .count();
        if (count > 0) {
            res.put("code", 500);
            res.put("msg", "你已加入该社团，无需重复加入");
            return res;
        }

        // 3. 插入用户-社团关联记录到数据库
        UserSociety us = new UserSociety();
        us.setUserId(userId);
        us.setSocietyId(societyId);
        userSocietyService.save(us);

        res.put("code", 200);
        res.put("msg", "加入成功");
        return res;
    }

    // 查询我加入的社团
    @GetMapping("/myJoin")
    public Map<String, Object> myJoin(@RequestParam Long userId) {
        Map<String, Object> res = new HashMap<>();
        // 1.根据userId查询用户关联的所有社团ID
        List<UserSociety> userSocietyList = userSocietyService.lambdaQuery()
                .eq(UserSociety::getUserId, userId)
                .list();
        if(userSocietyList.isEmpty()){
            res.put("code", 200);
            res.put("data", new ArrayList<>());
            return res;
        }
        // 提取社团id集合
        List<Long> societyIdList = userSocietyList.stream()
                .map(UserSociety::getSocietyId)
                .toList();
        // 批量查询社团详情（从数据库真实查询）
        List<Society> societyList = societyService.lambdaQuery()
                .in(Society::getId, societyIdList)
                .list();
        // 组装返回前端需要的字段
        List<Map<String, Object>> result = new ArrayList<>();
        for(Society s : societyList){
            Map<String, Object> map = new HashMap<>();
            map.put("societyId", s.getId());
            map.put("groupName", s.getGroupName());
            map.put("msgSuffix", s.getMsgSuffix());
            result.add(map);
        }
        res.put("code", 200);
        res.put("data", result);
        return res;
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteSociety(
            @RequestParam Long userId,
            @RequestParam Long societyId
    ) {
        Map<String, Object> res = new HashMap<>();
        // 校验：只能管理员删除自己创建的社团
        Society society = societyService.getById(societyId);
        if(society == null){
            res.put("code",500);
            res.put("msg","社团不存在");
            return res;
        }
        if(!society.getAdminUserId().equals(userId)){
            res.put("code",500);
            res.put("msg","仅创建管理员可删除该社团");
            return res;
        }
        // 级联删除：社团、关联用户、预设地点
        societyService.removeById(societyId);
        userSocietyService.lambdaUpdate()
                .eq(UserSociety::getSocietyId, societyId)
                .remove();
        eventService.lambdaUpdate()
                .eq(SocietyEvent::getSocietyId, societyId)
                .remove();
        res.put("code",200);
        res.put("msg","删除社团成功");
        return res;
    }

    @PostMapping("/quit")
    public Map<String, Object> quitSociety(
            @RequestParam Long userId,
            @RequestParam Long societyId
    ) {
        Map<String, Object> res = new HashMap<>();
        long count = userSocietyService.lambdaQuery()
                .eq(UserSociety::getUserId, userId)
                .eq(UserSociety::getSocietyId, societyId)
                .count();
        if(count == 0){
            res.put("code",500);
            res.put("msg","你未加入该社团");
            return res;
        }
        // 删除用户社团关联记录
        userSocietyService.lambdaUpdate()
                .eq(UserSociety::getUserId, userId)
                .eq(UserSociety::getSocietyId, societyId)
                .remove();
        res.put("code",200);
        res.put("msg","退出社团成功");
        return res;
    }
}
