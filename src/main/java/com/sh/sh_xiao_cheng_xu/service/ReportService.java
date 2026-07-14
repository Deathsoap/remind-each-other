package com.sh.sh_xiao_cheng_xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.sh_xiao_cheng_xu.entity.ReportInfo;

import java.util.List;

public interface ReportService extends IService<ReportInfo> {
    String publishReport(Long userId, Long societyId, Long eventId, String desc, String imgUrls);
    List<ReportInfo> getHistoryBySociety(Long societyId);
}