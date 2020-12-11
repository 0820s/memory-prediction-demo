package com.sjtu.demo.dao;

import com.sjtu.demo.mapper.PredictionMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;

@Repository
public class PredictionDao {
    @Resource
    PredictionMapper predictionMapper;

    protected Date getDate(Date oldDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.DATE,day);
        Date date = new Date(calendar.getTimeInMillis());
        return date;
    }

    public float getProbability(String ip, Date date){
        return predictionMapper.getProbability(ip,date).floatValue();
    }

    public float[] get7Probability(String ip, Date date){
        float[] result=new float[7];
        for(int i=0;i>-7;i--){
            Float cur=predictionMapper.getProbability(ip,getDate(date,i));
            if(cur==null){
                result[-i]=0;
            }
            else{
                result[-i]=cur.floatValue();
            }
        }
        return result;
    }

    public void save(String ip, Date date, float probability){
        Float cur=predictionMapper.getProbability(ip,date);
        if(cur==null){
            predictionMapper.save(ip,date,probability);
        }
        else{
            predictionMapper.update(ip,date,probability);
        }
    }
}
