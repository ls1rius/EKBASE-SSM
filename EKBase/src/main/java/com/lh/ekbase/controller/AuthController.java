package com.lh.ekbase.controller;

import com.lh.ekbase.mapper.UserMapper;
import com.lh.ekbase.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@RestController
public class AuthController extends ControllerWithSession {
    @Autowired
    private UserMapper userMapper;

    private void setLogInUser(User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    private boolean hasLogin() {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }

    /**
     * @api {get} /login?username=xxx&password=xxx 登录
     * @apiGroup Authentication
     * @apiParam {string} username 用户名
     * @apiParam {string} password 密码
     * @apiSuccess {boolean} result 成功返回true，失败返回false
     * @apiSuccess {boolean} msg 附带消息
     * @apiSuccessExample {json} 成功消息样例
     * {"result":true,"msg":"successfully login!"}
     * @apiErrorExample {json} 失败消息样例
     * {"result":false,"msg":"You have already log in!"}
     */
    @RequestMapping("login")
    public Object login(@RequestParam String username, @RequestParam String password) {
        HashMap<String, Object>json = new HashMap<>();
        User user = null;
        String msg;
        if(hasLogin()) {
            msg = "You have already log in!";
        }
        else {
            user = userMapper.checkUserInfo(username, password);
            //如果登录成功，写入session
            if(user != null) {
                setLogInUser(user);
                msg = "successfully login!";
            }
            else {
                msg = "login failed! check your username and password!";
            }
        }
        json.put("result", user != null);
        json.put("msg", msg);
        return json;
    }

    /**
     * @api {get} /logout 注销登陆
     * @apiGroup Authentication
     * @apiSuccess {boolean} result 成功返回true，失败返回false
     * @apiSuccess {boolean} msg 附带消息
     * @apiSuccessExample {json} 成功消息样例
     * {"result":true,"msg":"successfully log out!"}
     * @apiErrorExample {json} 失败消息样例
     * {"result":false,"msg":"you haven't log in!"}
     */
    @RequestMapping("/logout")
    public Object logout() {
        HashMap<String, Object>json = new HashMap<>();
        boolean ok = false;
        String msg;
        if(hasLogin()) {
            setLogInUser(null);
            ok = true;
            msg = "successfully log out!";
        }
        else {
            msg = "you haven't log in!";
        }
        json.put("result", ok);
        json.put("msg", msg);
        return json;
    }

    /**
     * @api {get} /getCurUser 获取当前登录的用户
     * @apiGroup Authentication
     * @apiSuccess {Object} result 如果已登录返回一个User，否则返回空
     * @apiSuccessExample {json} 成功消息样例
     * {"id":1,"username":"root","name":null,"email":null}
     */
    @RequestMapping("getCurUser")
    public Object getCurUser() {
        HttpSession session = request.getSession();
        return session.getAttribute("user");
    }
    /**
     * @api {get} /register?username=xxx&password=xxx&name=xxx&email=xxx&phone=xxx 注册
     * @apiGroup Authentication
     * @apiParam {string} username 用户名
     * @apiParam {string} password 密码
     * @apiParam {string} name 姓名
     * @apiParam {string} email email
     * @apiParam {string} phone 联系电话
     * @apiSuccess {String} result 返回注册成功
     * @apiSuccessExample {json} 成功消息样例
     * {"result":"success","msg":"Register sucessfully!"}
     * @apiErrorExample {json} 失败消息样例
     * {"result":"error","msg":"The username is already existed!"}
     */
    @RequestMapping("register")
    public Object register(User user){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","success");
        if(((HashMap<String,Object>)isUserNameExist(user.getUsername())).get("result").toString().equals("exist")){
            json.put("result","error");
            json.put("msg","The username is already existed!");
        }
        else{
            userMapper.register(user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getPhone());
            json.put("result","success");
            json.put("msg","Register sucessfully!");
        }
        return json;
    }
    /**
     * @api {get} /isUserNameExist?username=xxx 检查用户名是否已存在
     * @apiGroup Authentication
     * @apiParam {string} username 用户名
     * @apiSuccess {String} msg 附带消息
     * @apiSuccessExample {json} 成功消息样例
     * {"msg":"unexist"}
     * @apiErrorExample {json} 失败消息样例
     * {"msg":"exist"}
     */
    @RequestMapping("isUserNameExist")
    public Object isUserNameExist(@RequestParam String username){
        HashMap<String,Object>json = new HashMap<>();
        if(userMapper.checkUserName(username)==null){
            json.put("result","unexist");
        }
        else{
            json.put("result","exist");
        }
        return json;
    }


    @RequestMapping("/updateSex")
    public Object updateSex(@RequestParam String username,@RequestParam String sex){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            userMapper.updateSex(username,sex);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }

    @RequestMapping("/updateAge")
    public Object updateSex(@RequestParam String username,@RequestParam int age) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("result", "error");
        try {
            userMapper.updateAge(username, age);
            json.put("result", "success");
        } catch (Exception IO) {
            json.put("result", "error");
        }
        return json;
    }

    @RequestMapping("/updateName")
    public Object updateName(@RequestParam String username,@RequestParam String name){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            userMapper.updateName(username,name);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }

    @RequestMapping("/updateEmail")
    public Object updateEmail(@RequestParam String username,@RequestParam String email){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            userMapper.updateEmail(username,email);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }

    @RequestMapping("/updatePhone")
    public Object updatePhone(@RequestParam String username,@RequestParam String phone){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            userMapper.updatePhone(username,phone);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }

    @RequestMapping("/updateHeadImage")
    public Object updateHeadImage(@RequestParam String username,@RequestParam String headImage){
        HashMap<String,Object>json = new HashMap<>();
        json.put("result","error");
        try{
            userMapper.updateHeadImage(username,headImage);
            json.put("result","success");
        }catch (Exception IO){
            json.put("result","error");
        }
        return json;
    }
}
