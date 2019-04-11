package com.lh.ekbase.controller;

import com.lh.ekbase.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * @api {get} /getFollowings?userId=xx 获取某用户关注的人
     * @apiGroup User
     * @apiParam {int} userId 用户的id
     * @apiSuccessExample {json} 成功消息样例
     * [{"id":2,"username":"u1","name":"asd","email":"asd"},{"id":3,"username":"u2","name":"asd","email":"asd"},{"id":4,"username":"u3","name":"asd","email":"asd"}]
     */
    @RequestMapping("/getFollowings")
    public Object getFollowings(@RequestParam int userId) {
        return userMapper.getFollowings(userId);
    }

    /**
     * @api {get} /getFollowers?userId=xx 获取关注某用户的人
     * @apiGroup User
     * @apiParam {int} userId 用户的id
     * @apiSuccessExample {json} 成功消息样例
     * [{"id":2,"username":"u1","name":"asd","email":"asd"},{"id":3,"username":"u2","name":"asd","email":"asd"},{"id":4,"username":"u3","name":"asd","email":"asd"}]
     */
    @RequestMapping("/getFollowers")
    public Object getFollowers(@RequestParam int userId) {
        return userMapper.getFollowers(userId);
    }



}
