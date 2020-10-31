package com.sjtu.demo.entities;

import java.sql.Date;

public class Record {
    private Date timestamp;
    private String ip;
    private float readBandwidth;
    private float writeBandwidth;
    private float readLatency;
    private int ce;
    private String bandID;

    public Record() {
    }

    public Record(Date timestamp, String ip, float readBandwidth, float writeBandwidth, float readLatency, int ce, String bandID) {
        this.timestamp = timestamp;
        this.ip = ip;
        this.readBandwidth = readBandwidth;
        this.writeBandwidth = writeBandwidth;
        this.readLatency = readLatency;
        this.ce = ce;
        this.bandID = bandID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getReadBandwidth() {
        return readBandwidth;
    }

    public void setReadBandwidth(float readBandwidth) {
        this.readBandwidth = readBandwidth;
    }

    public float getWriteBandwidth() {
        return writeBandwidth;
    }

    public void setWriteBandwidth(float writeBandwidth) {
        this.writeBandwidth = writeBandwidth;
    }

    public float getReadLatency() {
        return readLatency;
    }

    public void setReadLatency(float readLatency) {
        this.readLatency = readLatency;
    }

    public int getCe() {
        return ce;
    }

    public void setCe(int ce) {
        this.ce = ce;
    }

    public String getBandID() {
        return bandID;
    }

    public void setBandID(String bandID) {
        this.bandID = bandID;
    }
}
