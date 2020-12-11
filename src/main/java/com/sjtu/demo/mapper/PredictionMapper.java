package com.sjtu.demo.mapper;

import org.apache.ibatis.annotations.*;

import java.sql.Date;

@Mapper
public interface PredictionMapper {
    @Select("select probability from prediction where ip=#{ip} and date=#{date, jdbcType=DATE}")
    public Float getProbability(@Param("ip")String ip, @Param("date")Date date);

    @Insert("insert into prediction(ip,date,probability) values(#{ip}, #{date, jdbcType=DATE}, #{probability})")
    public void save(@Param("ip")String ip, @Param("date")Date date, @Param("probability")float probability);

    @Update("update prediction set probability=#{probability} where ip=#{ip} and date=#{date, jdbcType=DATE}")
    public void update(@Param("ip")String ip, @Param("date")Date date, @Param("probability")float probability);
}
