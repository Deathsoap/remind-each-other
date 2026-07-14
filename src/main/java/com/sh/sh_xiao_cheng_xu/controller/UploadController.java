package com.sh.sh_xiao_cheng_xu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    // 你的cpolar固定二级域名，全局统一公网前缀
    private static final String CPOLAR_BASE = "https://61f8b47a.r34.cpolar.top";


    @PostMapping("/image")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> res = new HashMap<>();
        // 本地磁盘保存路径
        String savePath = "D:/upload/";
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        // 生成唯一文件名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        File saveFile = new File(savePath + fileName);
        file.transferTo(saveFile);

        // 直接返回公网可访问地址，手机/外地朋友都能打开图片
        String imgUrl = CPOLAR_BASE + "/upload/" + fileName;
        res.put("code", 200);
        res.put("data", imgUrl);
        return res;
    }
}
