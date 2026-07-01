package com.sh.sh_xiao_cheng_xu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("society_event")
public class SocietyEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long societyId;
    private String eventName;
    private LocalDateTime createTime;
}
