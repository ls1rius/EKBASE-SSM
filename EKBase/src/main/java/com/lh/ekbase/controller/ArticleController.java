package com.lh.ekbase.controller;

import com.lh.ekbase.mapper.ArticleMapper;
import com.lh.ekbase.mapper.UserMapper;
import com.lh.ekbase.entity.Article;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * @api {get} /getArticle 获取文章（使用id）
     * @apiGroup Article
     * @apiParam {string} id 文章id
     * @apiSuccessExample {json} 成功消息样例
     * {"id":1,"title":"文章1","content":"噜啦啦噜啦啦噜啦啦噜啦啦","owner_id":0,"hit_cnt":0}
     */
    @RequestMapping("/getArticle")
    public Object getArticle(@RequestParam int id) {
        return articleMapper.getArticle(id);
    }

    /**
     * @api {get} /searchArticle 搜索文章
     * @apiGroup Article
     * @apiParam {string} str 搜索模式串
     * @apiSuccessExample {json} 成功消息样例
     * [{"id":1,"title":"文章1","content":"asd噜啦啦噜啦啦噜啦啦噜啦啦","owner_id":0,"hit_cnt":0},{"id":2,"title":"文章2","content":"asd鲁拉拉拉","owner_id":0,"hit_cnt":0},{"id":3,"title":"文章3","content":"asd","owner_id":0,"hit_cnt":0},{"id":4,"title":"文章4","content":"asdasdasd","owner_id":0,"hit_cnt":0}]
     */
    @RequestMapping("/searchArticles")
    public Object searchArticles(@RequestParam String str,@RequestParam(required = false, defaultValue = "0") int start ,@RequestParam(required = false, defaultValue = "4") int cnt) {

        return articleMapper.searchArticles(str,start,cnt);
    }

    @RequestMapping("/getSearchArticlesCount")
    public Object getSearchArticlesCount(@RequestParam String str) {

        return articleMapper.getSearchArticlesCount(str);
    }

    @RequestMapping("/getRcmdArticle")
    public Object getRcmdArticle() {
        List<Article> articles = articleMapper.getRcmdArticle();
        for (Article article : articles) {
            article.setOwner(userMapper.getUserInfo(article.getOwnerId()));
        }
        return articles;
    }

    @RequestMapping("/getCarousel")
    public Object getCarousel(@RequestParam(required = false, defaultValue = "5") int cnt) {
        List<Article> list = articleMapper.getCarousel(cnt);
        for (Article item : list) {
            item.setContent(item.getContent().substring(0,100));
        }
        return list;
    }

    @RequestMapping("/getWordCloud")
    public Object getWordCloud(@RequestParam(required = false, defaultValue = "100") int cnt) {
        List<String> allContents = articleMapper.getAllArticleContents();
        HashMap<String, Integer> wordCnt = new HashMap<>();

        for (String content : allContents) {
            //去除标点和空白字符和数字
            content = content.replaceAll("[\\pP‘’“”\\s　 0-9]", "");
            //统计词语出现次数
            for (Term word : ToAnalysis.parse(content)) {
                //1个字的去掉，非名词的去掉
                if(word.getName().length() <= 1 || word.getNatureStr().charAt(0) != 'n')continue;
                wordCnt.put(word.getName(), wordCnt.getOrDefault(word.getName(), 0) + 1);
            }
        }

        //加点比特币和区块链  显得真实
        wordCnt.put("比特币", 6000);
        wordCnt.put("区块链", 4000);
        ArrayList<Map<String, Object>>res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCnt.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            Map<String, Object> mp = new HashMap<>();
            mp.put("word", k); mp.put("weight", v);
            res.add(mp);
        }
        res.sort((a, b) -> {
            Integer x = (Integer)a.get("weight");
            Integer y = (Integer)b.get("weight");
            return x < y ? 1 : -1;
        });
        //取前cnt个
        return res.stream().limit(cnt).collect(Collectors.toList());
    }


    @RequestMapping("getTheReviewArticles")
    public Object getTheReviewArticles(@RequestParam(required = false,defaultValue = "0") int start,
                                       @RequestParam(required = false,defaultValue = "10") int cnt){
        return articleMapper.getTheReviewArticles(start,cnt);
    }

    @RequestMapping("getTheSpecificArticles")
    public Object getTheSpecificArticles(@RequestParam(required = false,defaultValue = "0") int start,
                                         @RequestParam(required = false,defaultValue = "10") int cnt,
                                         @RequestParam(required = false,defaultValue = "1") int status){
        return articleMapper.getTheSpecificArticles(start,cnt,status);
    }

    @RequestMapping("updateArticleStatus")
    public Object updateArticleStatus(int id,int status){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            articleMapper.updateArticleStatus(id,status);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }


    @RequestMapping("getMySpecificArticles")
    public Object getMySpecificArticles(@RequestParam String username,
                                         @RequestParam(required = false,defaultValue = "0") int start,
                                         @RequestParam(required = false,defaultValue = "10") int cnt,
                                         @RequestParam(required = false,defaultValue = "1") int status){
        return articleMapper.getMySpecificArticles(username,start,cnt,status);
    }

    @RequestMapping("uploadArticles")
    public Object uploadArticles(Article article){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            if(article.getCoverUrl()==null)
                article.setCoverUrl("https://source.unsplash.com/random/"+Math.round(Math.random()*10000));
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            article.setDate(new Date());
            articleMapper.uploadArticle(article.getTitle(),article.getContent(),article.getOwnerId(),article.getCoverUrl(),article.getDate());
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }

}
