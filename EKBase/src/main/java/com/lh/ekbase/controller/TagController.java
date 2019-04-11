package com.lh.ekbase.controller;

import com.lh.ekbase.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    TagMapper tagMapper;

    @RequestMapping("/getPopularTags")
    public Object getPopularTags() {
        return tagMapper.getPopularTags();
    }
}
