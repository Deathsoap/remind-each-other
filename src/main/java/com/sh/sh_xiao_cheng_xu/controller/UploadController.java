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

    @PostMapping("/image")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> res = new HashMap<>();
        // 本地存储路径，自行修改
        String savePath = "D:/upload/";
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        // 生成唯一文件名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        File saveFile = new File(savePath + fileName);
        file.transferTo(saveFile);

        // 返回图片访问地址，本地测试临时模拟
        String imgUrl = "http://127.0.0.1:8080/upload/" + fileName;
        res.put("code", 200);
        res.put("data", imgUrl);
        return res;
    }
}
