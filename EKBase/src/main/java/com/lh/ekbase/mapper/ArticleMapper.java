package com.lh.ekbase.mapper;

import com.lh.ekbase.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Date;
import java.util.List;

public interface ArticleMapper {
    Article getArticle(int id);
    List<Article> searchArticles(@Param("str")String str,@Param("start")int start ,@Param("cnt")int cnt);
    Integer getSearchArticlesCount(String str);
    List<Article> getRcmdArticle();
    List<Article> getCarousel(int cnt);
    List<String> getAllArticleContents();
    void setArticleCoverUrl(@Param("id") int id, @Param("url") String url);
    List<Article> getTheReviewArticles(@Param("start")int start ,@Param("cnt")int cnt);
    List<Article> getTheSpecificArticles(@Param("start")int start ,@Param("cnt")int cnt ,@Param("status")int status);
    void updateArticleStatus(@Param("id")int id, @Param("status")int status);
    List<Article> getMySpecificArticles(@Param("username")String username,@Param("start")int start,@Param("cnt")int cnt ,@Param("status")int status);
    void uploadArticle(@Param("title") String title,@Param("content") String content,@Param("ownerId")int ownerId,@Param("coverUrl")String coverUrl,@Param("date")Date date);
}
