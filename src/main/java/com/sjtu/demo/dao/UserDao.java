package com.sjtu.demo.dao;

import com.sjtu.demo.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {
//    private static Map<String, String> users=null;
//    static {
//        users=new HashMap<>();
//        users.put("admin","123456");
//    }
//
//    public void save(String name, String password){
//        users.put(name,password);
//    }
//
//    public boolean checkPassword(String name, String password){
//        if(users.containsKey(name)){
//            if(users.get(name).equals(password)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean checkName(String name){
//        if(users.containsKey(name)){
//           return true;
//        }
//        return false;
//    }

    @Resource
    UserMapper userMapper;

    public void save(String name, String password){
        userMapper.addUser(name, password);
    }

    public boolean checkPassword(String name, String password){
        if(userMapper.getPasswordByName(name)!=null && userMapper.getPasswordByName(name).equals(password)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean containsName(String name){
        if(userMapper.getPasswordByName(name)==null){
            return true;
        }
        else{
            return false;
        }
    }
}
