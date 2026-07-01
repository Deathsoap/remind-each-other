package com.sh.sh_xiao_cheng_xu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.sh_xiao_cheng_xu.entity.WxUser;

public interface WxUserService extends IService<WxUser> {
    WxUser getByOpenId(String openId);
    WxUser createUser(String openId);
}
