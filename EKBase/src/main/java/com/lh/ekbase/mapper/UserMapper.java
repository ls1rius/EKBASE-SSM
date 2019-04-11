package com.lh.ekbase.mapper;

import com.lh.ekbase.entity.User;
import com.lh.ekbase.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper
{
    User checkUserInfo(@Param("username") String username, @Param("password") String password);
    User checkUserName(@Param("username") String username);
    User getUserInfo(int userId);
    List<Tag> getFavoriteTags(int userId);
    List<User> getFollowings(int userId);
    List<User> getFollowers(int userId);
    void register(@Param("username") String username,@Param("password") String password,@Param("name") String name,@Param("email") String email,@Param("phone") String phone);
    void updateSex(@Param("username") String username, @Param("sex") String sex);
    void updateAge(@Param("username") String username, @Param("age") int age);
    void updateName(@Param("username") String username, @Param("name") String name);
    void updateEmail(@Param("username") String username, @Param("email") String email);
    void updatePhone(@Param("username") String username, @Param("phone") String phone);
    void updateHeadImage(@Param("username") String username, @Param("headImage") String headImage);


}
