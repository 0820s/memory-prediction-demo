package com.sjtu.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;

@Mapper
public interface PredictionMapper {
    @Select("select probability from prediction where ip=#{ip} and date=#{date, jdbcType=DATE}")
    public Float getProbability(@Param("ip")String ip, @Param("date")Date date);
}
