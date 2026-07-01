package com.sh.sh_xiao_cheng_xu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("report_info")
public class ReportInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer societyId;
    private Long eventId;
    private Long publisherUid;
    private String description;
    private String imgUrl;
    private LocalDateTime createTime;
}
