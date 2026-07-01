package com.sh.sh_xiao_cheng_xu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("society")
public class Society {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String groupName;
    private String msgSuffix;
    private Long adminUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
