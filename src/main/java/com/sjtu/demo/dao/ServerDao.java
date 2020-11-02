package com.sjtu.demo.dao;

import com.sjtu.demo.entities.Server;
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
        String serverBrand=serverMapper.getServerBrandFromIp(ip);
        if(serverBrand==null){
            return "unknown";
        }else{
            return serverBrand;
        }
    }

    public String getProcVersionFromIp(String ip){
        String procVersion=serverMapper.getProcVersionFromIp(ip);
        if(procVersion==null){
            return "unknown";
        }else{
            return procVersion;
        }
    }

    public String getMemManufactoryFromIp(String ip){
        String memManufactory=serverMapper.getMemManufactoryFromIp(ip);
        if(memManufactory==null){
            return "unknown";
        }else{
            return memManufactory;
        }
    }

    public String getMemSpeedFromIp(String ip){
        String memSpeed=serverMapper.getMemSpeedFromIp(ip);
        if(memSpeed==null){
            return "unknown";
        }else{
            return memSpeed;
        }
    }

    public String getMemPartFromIp(String ip){
        String memPart=serverMapper.getMemPartFromIp(ip);
        if(memPart==null){
            return "unknown";
        }else{
            return memPart;
        }
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

    public Server getInfo(String ip){
        Server server=new Server(getUserFromIp(ip), ip, getServerBrandFromIp(ip), getProcVersionFromIp(ip), getMemManufactoryFromIp(ip), getMemSpeedFromIp(ip), getMemPartFromIp(ip));
        return server;
    }
}
