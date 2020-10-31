package com.sjtu.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper{
    @Select("select password from user where name = #{name}")
    public String getPasswordByName(String name);

    @Insert("insert into user(name, password) values(#{name},#{password})")
    public void addUser(@Param("name") String name, @Param("password") String password);
}
