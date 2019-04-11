package com.lh.ekbase.controller;

import com.lh.ekbase.entity.Node;
import com.lh.ekbase.mapper.CatalogMapper;
import com.lh.ekbase.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class CatalogControl {
    @Autowired
    private CatalogMapper catalogMapper;


    @RequestMapping("clearCatalog")
    public void clearCatalog(String code,Integer ownerId){
        if(code.equals("123456")){
            catalogMapper.clearCatalog(ownerId);
        }
    }

    @RequestMapping("uploadCatalog")
    public void uploadCatalog(Node tree){

        catalogMapper.uploadCatalog(tree.getId(),tree.getPid(),tree.getLabel(),tree.getUrl(),tree.getOwnerId());

    }

    @RequestMapping("getCatalog")
    public Object getCatalog(Integer ownerId){


        return catalogMapper.getCatalog(ownerId);
    }
}
