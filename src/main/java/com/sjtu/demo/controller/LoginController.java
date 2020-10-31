package com.sjtu.demo.controller;

import com.sjtu.demo.dao.ServerDao;
import com.sjtu.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserDao userDao;
    @Autowired
    ServerDao serverDao;

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && userDao.checkPassword(username,password)){
            //登陆成功，防止表单重复提交，可以重定向到主页
            String[] ip=serverDao.getIpsFromUser(username);
            if(ip.length==0){
                session.setAttribute("ip",null);
            }
            else{
                session.setAttribute("ip",ip[0]);
            }
            session.setAttribute("loginUser",username);

            return "redirect:/main.html";
        }else{
            //登陆失败

            map.put("msg","用户名密码错误");
            return  "login";
        }

    }

    @PostMapping(value="/user/signup")
    public  String signup(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Map<String,Object> map, HttpSession session) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            if (!userDao.containsName(username)) {
                userDao.save(username, password);
                String[] ip=serverDao.getIpsFromUser(username);
                if(ip.length==0){
                    session.setAttribute("ip",null);
                }
                else{
                    session.setAttribute("ip",ip[0]);
                }
                session.setAttribute("loginUser", username);
                return "redirect:/main.html";
            } else {
                map.put("msg", "用户名已存在");
                return "signup";
            }

        } else {
            map.put("msg", "请输入用户名与密码");
            return "signup";
        }
    }
}
