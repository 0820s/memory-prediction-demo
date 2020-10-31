package com.sjtu.demo.mapper;

import org.apache.ibatis.annotations.*;


@Mapper
public interface ServerMapper {
    @Select("select userName from server where ip=#{ip}")
    public String getUserFromIp(String ip);

    @Select("select ip from server where userName=#{userName}")
    public String[] getIpsFromUsername(String userName);

    @Select("select serverBrand from server where ip=#{ip}")
    public String getServerBrandFromIp(String ip);

    @Select("select procVersion from server where ip=#{ip}")
    public String getProcVersionFromIp(String ip);

    @Select("select memManufactory from server where ip=#{ip}")
    public String getMemManufactoryFromIp(String ip);

    @Select("select memSpeed from server where ip=#{ip}")
    public String getMemSpeedFromIp(String ip);

    @Select("select memPart from server where ip=#{ip}")
    public String getMemPartFromIp(String ip);

    @Insert("insert into server(ip, userName) values(#{ip},#{userName})")
    public void addIp(@Param("ip") String ip, @Param("userName") String userName);

    @Update("update server set serverBrand=#{serverBrand}, procVersion=#{procVersion}, memManufactory=#{memManufactory}, memSpeed=#{memSpeed}, memPart=#{memPart} where ip=#{ip}")
    public void addInfo(@Param("ip")String ip, @Param("serverBrand")String serverBrand, @Param("procVersion")String procVersion, @Param("memManufactory")String memManufactory, @Param("memSpeed")String memSpeed, @Param("memPart")String memPart);
}
