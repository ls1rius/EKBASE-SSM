package com.lh.ekbase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class DirectoryController {

    public class Node {
        public ArrayList<Node> children;

    }

    @RequestMapping("/getDirectoryStructure")
    public Object getDirectoryStructure() {
        return 0;
    }
}
