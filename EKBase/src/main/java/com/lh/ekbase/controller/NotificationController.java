package com.lh.ekbase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
public class NotificationController {

    @RequestMapping("/getNotifications")
    public Object GetNotifications() {
        ArrayList<String> notices = new ArrayList<>();
        notices.add("您关注的用户<i>Aimer</i>有更新");
        notices.add("您关注的话题<i>比特币</i>有更新");
        notices.add("<i>DLee</i>关注了您");
        notices.add("您关注的文章<i>区块链基础</i>有新修改");
        notices.add("<i>Tig_hen</i>评论了您");
        notices.add("你的文章<i>论市场与调研</i>通过了审核");
        notices.add("你的文章<i>货币经济理论</i>通过了审核");
        notices.add("你的文章<i>一带一路推动经济的发展</i>通过了审核");
        ArrayList<HashMap<String, Object>> res = new ArrayList<>();
        int minute = 14;
        int second = 14;
        for(String notice : notices) {
            HashMap<String, Object> mp = new HashMap<>();
            mp.put("text", notice);
            mp.put("date", "15:" + minute + ":" + second);
            minute += 3;
            second += 4;
            res.add(mp);
        }
        return res;
    }
}
