package com.sh.sh_xiao_cheng_xu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wx_user")
public class WxUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}