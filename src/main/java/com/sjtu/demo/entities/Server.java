package com.sjtu.demo.entities;

public class Server {
    private String userName;
    private int ip;
    private String serverBrand;
    private String procVersion;
    private String memManufactory;
    private String memSpeed;
    private String memPart;

    public Server() {
    }

    public Server(String userName, int ip, String serverBrand, String procVersion, String memManufactory, String memSpeed, String memPart) {
        this.userName = userName;
        this.ip = ip;
        this.serverBrand = serverBrand;
        this.procVersion = procVersion;
        this.memManufactory = memManufactory;
        this.memSpeed = memSpeed;
        this.memPart = memPart;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getServerBrand() {
        return serverBrand;
    }

    public void setServerBrand(String serverBrand) {
        this.serverBrand = serverBrand;
    }

    public String getProcVersion() {
        return procVersion;
    }

    public void setProcVersion(String procVersion) {
        this.procVersion = procVersion;
    }

    public String getMemManufactory() {
        return memManufactory;
    }

    public void setMemManufactory(String memManufactory) {
        this.memManufactory = memManufactory;
    }

    public String getMemSpeed() {
        return memSpeed;
    }

    public void setMemSpeed(String memSpeed) {
        this.memSpeed = memSpeed;
    }

    public String getMemPart() {
        return memPart;
    }

    public void setMemPart(String memPart) {
        this.memPart = memPart;
    }
}
