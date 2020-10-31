package com.sjtu.demo.dao;

import com.sjtu.demo.mapper.ServerMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;


@Repository
public class ServerDao {
    @Resource
    ServerMapper serverMapper;

    public void saveIp(String ip, String userName){
        serverMapper.addIp(ip, userName);
    }

    public String getUserFromIp(String ip){
        return serverMapper.getUserFromIp(ip);
    }

    public String[] getIpsFromUser(String userName){
        return serverMapper.getIpsFromUsername(userName);
    }

    public ArrayList<String> getIpFromUser(String userName){
        String[] ip=serverMapper.getIpsFromUsername(userName);
        ArrayList<String> list=new ArrayList<>();
        for(String s:ip){
            list.add(s);
        }
        return list;
    }

    public String getServerBrandFromIp(String ip){
        return serverMapper.getServerBrandFromIp(ip);
    }

    public String getProcVersionFromIp(String ip){
        return serverMapper.getProcVersionFromIp(ip);
    }

    public String getMemManufactoryFromIp(String ip){
        return serverMapper.getMemManufactoryFromIp(ip);
    }

    public String getMemSpeedFromIp(String ip){
        return serverMapper.getMemSpeedFromIp(ip);
    }

    public String getMemPartFromIp(String ip){
        return serverMapper.getMemPartFromIp(ip);
    }

    public boolean addInfo(String ip, String serverBrand, String procVersion, String memManufactory, String memSpeed, String memPart){
        if(containsIp(ip)){
            serverMapper.addInfo(ip, serverBrand,procVersion,memManufactory,memSpeed,memPart);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean containsIp(String ip){
        if(getUserFromIp(ip)==null){
            return false;
        }
        else{
            return true;
        }
    }
}
