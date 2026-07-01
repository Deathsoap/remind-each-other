package com.sh.sh_xiao_cheng_xu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    // 本地存储路径
    private final String savePath = "./upload/";
    // 本地访问地址前缀
    private final String localUrlPrefix = "http://127.0.0.1:8080/upload/";

    @PostMapping("/image")
    public String uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        // 创建文件夹
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        // 生成唯一文件名防止覆盖
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        // 保存文件
        file.transferTo(new File(savePath + fileName));
        // 返回图片访问地址
        return localUrlPrefix + fileName;
    }
}
