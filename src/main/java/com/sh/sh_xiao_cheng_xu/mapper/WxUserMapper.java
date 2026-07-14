package com.sh.sh_xiao_cheng_xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sh.sh_xiao_cheng_xu.entity.WxUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WxUserMapper extends BaseMapper<WxUser> {

    // 根据openId查询用户
    @Select("SELECT id, open_id FROM wx_user WHERE open_id = #{openId}")
    WxUser selectByOpenId(@Param("openId") String openId);

    // 新增用户
    @Insert("INSERT INTO wx_user(open_id, avatar_url, nick_name) VALUES(#{openId},#{avatarUrl},#{nickName})")
    int insert(WxUser wxUser);
}