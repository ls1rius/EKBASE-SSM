package com.lh.ekbase.controller;

import com.lh.ekbase.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lh.ekbase.controller.AuthController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UpLoadFiles extends ControllerWithSession{

    private String BASE_PATH = "/var/www/html/EKBaseFiles/";
//    private String BASE_PATH = "/Users/lihao/Desktop/";
    private String BASE_URL = "http://www.ls1rius.com/EKBaseFiles/";

    private String CUR_USER = "default";

    private AuthController auth;

    @PostMapping("/singleFile")
    public Object singleFileUpLoad(MultipartFile file) {
        if(request.getSession().getAttribute("user")!=null){
            CUR_USER = ((User)(request.getSession().getAttribute("user"))).getUsername();
        }
        String UPLOAD_FOLDER = BASE_PATH+CUR_USER;
        String ONLINE_PATH = BASE_URL+CUR_USER;
        Logger logger = LoggerFactory.getLogger(UpLoadFiles.class);
        HashMap<String,Object> json = new HashMap<>();
//        logger.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (Objects.isNull(file) || file.isEmpty()) {
//            logger.error("文件为空");
            json.put("result","file is empty");
            json.put("url",null);
        }

        try {
            //正则判断上传的文件的格式
            String imageReg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
            String videoReg = ".+(.FLV|.flv|.RMVB|.rvmb|.MP4|.mp4|.AVI|.avi|.WMV|.wmv)$";
//            Pattern pattern = Pattern.compile(reg);
//            Matcher matcher = pattern.matcher(file.getOriginalFilename());
//            System.out.println(matcher.find());
            if(Pattern.matches(imageReg, file.getOriginalFilename())) {
                UPLOAD_FOLDER = UPLOAD_FOLDER + "/images/";
                ONLINE_PATH = ONLINE_PATH + "/images/";
            }
            else if(Pattern.matches(videoReg, file.getOriginalFilename())) {
                UPLOAD_FOLDER = UPLOAD_FOLDER + "/videos/";
                ONLINE_PATH = ONLINE_PATH + "/videos/";
            }
            else {
                UPLOAD_FOLDER = UPLOAD_FOLDER + "/files/";
                ONLINE_PATH = ONLINE_PATH + "/files/";
            }


            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);
//            logger.debug("文件写入成功...");
            json.put("result","success");
            json.put("url",ONLINE_PATH + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            json.put("result","error");
            json.put("url",null);
        }
        return json;
    }


    @RequestMapping("/files")
    public Object filesUpload(MultipartFile[] files) {
        HashMap<String,Object> json = new HashMap<>();
        //判断file数组不能为空并且长度大于0
        if(files!=null&&files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i<files.length;i++){
                MultipartFile file = files[i];
                //保存文件
                json.put(String.valueOf(i),(HashMap<String,Object>)singleFileUpLoad(file));
            }
        }
        return json;
    }
}
