package com.sjtu.demo.dao;

import com.sjtu.demo.mapper.RecordMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;

@Repository
public class RecordDao {
    @Resource
    RecordMapper recordMapper;

    protected Date getDate(Date oldDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.DATE,day);
        Date date = new Date(calendar.getTimeInMillis());
        return date;
    }


    public float getReadBandwidth(String ip, Date timestamp){
        Float readBandwidth=recordMapper.getReadBandwidth(ip,timestamp);
        if(readBandwidth==null){
            return 0;
        }else{
            return readBandwidth.floatValue();
        }
    }

    public float getWriteBandwidth(String ip, Date timestamp){
        Float writeBandwidth=recordMapper.getWriteBandwidth(ip,timestamp);
        if(writeBandwidth==null){
            return 0;
        }else{
            return writeBandwidth.floatValue();
        }
    }

    public float getReadLatency(String ip, Date timestamp){
        Float readLatency=recordMapper.getReadLatency(ip,timestamp);
        if(readLatency==null){
            return 0;
        }else{
            return readLatency.floatValue();
        }
    }

    public int getCE(String ip, Date timestamp){
        Integer CECount=recordMapper.getCE(ip,timestamp);
        if(CECount==null){
            return 0;
        }else{
            return CECount.intValue();
        }
    }

    public String getBankID(String ip, Date timestamp){
        String bankID=recordMapper.getBankID(ip,timestamp);
        if(bankID==null){
            return "none";
        }else{
            return bankID;
        }
    }

    public float[] get7ReadBandwidth(String ip, Date timestamp){
        float[] result=new float[7];
        for(int i=0;i>-7;i--) {
            Float cur=recordMapper.getReadBandwidth(ip, getDate(timestamp,i));
            if(cur==null){
                result[-i]=0;
            }
            else{
                result[-i]=cur.floatValue();
            }
        }
        return result;
    }

    public float[] get7WriteBandwidth(String ip, Date timestamp){
        float[] result=new float[7];
        for(int i=0;i>-7;i--) {
            Float cur=recordMapper.getWriteBandwidth(ip, getDate(timestamp,i));
            if(cur==null){
                result[-i]=0;
            }
            else{
                result[-i]=cur.floatValue();
            }
        }
        return result;
    }

    public float[] get7ReadLatency(String ip, Date timestamp){
        float[] result=new float[7];
        for(int i=0;i>-7;i--) {
            Float cur=recordMapper.getReadLatency(ip, getDate(timestamp,i));
            if(cur==null){
                result[-i]=0;
            }
            else{
                result[-i]=cur.floatValue();
            }
        }
        return result;
    }

    public int[] get7CE(String ip, Date timestamp){
        int[] result=new int[7];
        for(int i=0;i>-7;i--) {
            Integer cur=recordMapper.getCE(ip, getDate(timestamp,i));
            if(cur==null){
                result[-i]=0;
            }
            else{
                result[-i]=cur.intValue();
            }
        }
        return result;
    }

    public String[] get7BankID(String ip, Date timestamp){
        String[] result=new String[7];
        for(int i=0;i>-7;i--) {
            String cur=recordMapper.getBankID(ip, getDate(timestamp,i));
            if(cur==null){
                result[-i]="unknown";
            }
            else{
                result[-i]=cur;
            }
        }
        return result;
    }

    public void save(String ip, Date timestamp, float readBandwidth, float writeBandwidth, float readLatency, int ce, String bankID){
        if(recordMapper.count(ip,timestamp).intValue()==0){
            recordMapper.save(ip,timestamp,readBandwidth,writeBandwidth,readLatency,ce,bankID);
        }
        else{
            recordMapper.update(ip,timestamp,readBandwidth,writeBandwidth,readLatency,ce,bankID);
        }
    }
}
