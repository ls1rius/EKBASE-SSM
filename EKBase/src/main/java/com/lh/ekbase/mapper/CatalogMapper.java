package com.lh.ekbase.mapper;

import com.lh.ekbase.entity.Article;
import com.lh.ekbase.entity.Node;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatalogMapper {
    void clearCatalog(Integer ownerId);
    void uploadCatalog(@Param("id") String id,@Param("pid") String pid,@Param("label") String label,@Param("url") String url,@Param("ownerId") Integer ownerId);
    List<Node>getCatalog(Integer ownerId);
}
