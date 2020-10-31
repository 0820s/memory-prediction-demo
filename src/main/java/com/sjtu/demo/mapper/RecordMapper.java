package com.sjtu.demo.mapper;

import org.apache.ibatis.annotations.*;

import java.sql.Date;

@Mapper
public interface RecordMapper {
    @Select(("select count(1) from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}"))
    public Integer count(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Select("select readBandwidth from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public Float getReadBandwidth(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Select("select writeBandwidth from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public Float getWriteBandwidth(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Select("select readLatency from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public Float getReadLatency(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Select("select ce from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public Integer getCE(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Select("select bankID from record where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public String getBankID(@Param("ip")String ip, @Param("timestamp") Date timestamp);

    @Insert("insert into record(ip, timestamp, readBandwidth, writeBandwidth, readLatency, ce, bankID) values(#{ip}, #{timestamp, jdbcType=DATE}, #{readBandwidth}, #{writeBandwidth}, #{readLatency}, #{ce}, #{bankID})")
    public void save(@Param("ip")String ip, @Param("timestamp")Date timestamp, @Param("readBandwidth")float readBandwidth, @Param("writeBandwidth")float writeBandwidth, @Param("readLatency")float readLatency, @Param("ce")int ce, @Param("bankID")String bankID);

    @Update("update record set ip=#{ip} timestamp=#{timestamp} readBandwidth=#{readBandwidth} writeBandwidth=#{writeBandwidth} readLatency=#{readLatency} ce=#{ce} bankID=#{bankID} where ip=#{ip} and timestamp=#{timestamp, jdbcType=DATE}")
    public void update(@Param("ip")String ip, @Param("timestamp")Date timestamp, @Param("readBandwidth")float readBandwidth, @Param("writeBandwidth")float writeBandwidth, @Param("readLatency")float readLatency, @Param("ce")int ce, @Param("bankID")String bankID);
}
