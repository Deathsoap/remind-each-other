package com.sh.sh_xiao_cheng_xu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("upload_file")
public class UploadFile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileUrl;
    private String fileName;
    private Long fileSize;
    private Long uploadUid;
    private Long relateReportId;
    private LocalDateTime createTime;
}
