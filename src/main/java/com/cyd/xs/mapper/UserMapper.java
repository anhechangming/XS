package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cyd.xs.entity.User.User;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<com.cyd.xs.entity.User.User> {
    // 根据用户名查询用户（登录/注册校验用）
    com.cyd.xs.entity.User.User selectByUsername(@Param("username") String username);

    // 校验用户名是否已存在
    Integer countByUsername(@Param("username") String username);


    @Select("SELECT * FROM users WHERE id = #{userId}")
    User findById(Long userId);

    @Select("SELECT role FROM users WHERE id = #{userId}")
    String getUserRole(Long userId);

    @Select("SELECT display_name FROM users WHERE id = #{userId}")
    String getUserDisplayName(Long userId);
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户实体
     */
//    User selectByUsername(String username);

    /**
     * 更新用户信息
     * @param user 用户实体
     */
//    void update(User user);
}