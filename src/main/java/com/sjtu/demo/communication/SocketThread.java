package com.sjtu.demo.communication;

import com.sjtu.demo.dao.PredictionDao;
import com.sjtu.demo.dao.RecordDao;
import com.sjtu.demo.dao.ServerDao;
import com.sjtu.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.sql.Date;

@Service
public class SocketThread {
    @Autowired
    UserDao userDao;

    @Autowired
    ServerDao serverDao;

    @Autowired
    RecordDao recordDao;

    @Autowired
    PredictionDao predictionDao;

    @Async
    public void connect(Socket clientSocket) {
        //accept connection and communicate
        String buf = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String ip=clientSocket.getInetAddress().toString().substring(1);
            boolean login=serverDao.containsIp(ip);
            //System.out.println("new connect ready");

            //read
            while(true){
                buf = br.readLine();
                //System.out.println(Thread.currentThread().getName()+" client: " + buf);

                if(buf.startsWith("close")){
                    break;
                }
                else if(buf.startsWith("login")){
                    String[] logInfor=buf.split(",");
                    if(logInfor.length==3 && userDao.checkPassword(logInfor[1],logInfor[2])){
                        pw.println("success");
                        pw.flush();
                        login=true;
                        if(!serverDao.containsIp(ip)){
                            serverDao.saveIp(ip, logInfor[1]);
                        }
                        //System.out.println("success log in: "+ip);
                    }
                    else{
                        pw.println("refused");
                        pw.flush();
                        System.out.println("log in refused: "+ip);
                    }
                }
                else if(login && buf.startsWith("hardware")){
                    String[] hardwareConfig=buf.split(",");
                    if(hardwareConfig.length==6){
                        if(serverDao.addInfo(ip, hardwareConfig[1], hardwareConfig[2], hardwareConfig[3], hardwareConfig[4], hardwareConfig[5])){
                            System.out.println("success add hardware info");
                        }
                        else{
                            System.out.println("failed to add info");
                        }
                    }
                }
                else if(login && buf.startsWith("append")){
                    String[] data=buf.split(",");
                    if(data.length==6){
                        Date date = new Date(System.currentTimeMillis());
                        recordDao.save(ip,date,Float.parseFloat(data[1]),Float.parseFloat(data[2]),Float.parseFloat(data[3]),Integer.parseInt(data[4]),data[5]);

                        StringBuffer sample=new StringBuffer();
                        /*sample.append(serverDao.getServerBrandFromIp(ip)+",");
                        sample.append(serverDao.getProcVersionFromIp(ip)+",");
                        sample.append(serverDao.getMemManufactoryFromIp(ip)+",");
                        sample.append(serverDao.getMemSpeedFromIp(ip)+",");
                        sample.append(serverDao.getMemPartFromIp(ip));*/

                        int[] CEs=recordDao.get10CE(ip,date);
                        for(int ce:CEs){
                            sample.append(",");
                            sample.append(String.valueOf(ce));
                        }

                        String[] bankIDs=recordDao.get10BankID(ip,date);
                        for(String bankID:bankIDs){
                            sample.append(",");
                            sample.append(bankID);
                        }

                        float[] readBandwidths=recordDao.get10ReadBandwidth(ip,date);
                        for(float readBandwidth:readBandwidths){
                            sample.append(",");
                            sample.append(String.valueOf(readBandwidth));
                        }

                        float[] writeBandwidths=recordDao.get10WriteBandwidth(ip,date);
                        for(float writeBandwidth:writeBandwidths){
                            sample.append(",");
                            sample.append(String.valueOf(writeBandwidth));
                        }

                        float[] readLatencys=recordDao.get10ReadLatency(ip,date);
                        for(float readLatency:readLatencys){
                            sample.append(",");
                            sample.append(String.valueOf(readLatency));
                        }

                        float probability=0;
                        try{
                            String command="python /root/project3_sjtu/model/xgbst_new.py "+sample.toString();
                            Process proc=Runtime.getRuntime().exec(command);
                            proc.waitFor();
                            BufferedReader in=new BufferedReader(new InputStreamReader(proc.getInputStream()));
                            String line=in.readLine();
                            if(line!=null){
                                probability=Float.parseFloat(line);
                            }
                            in.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        predictionDao.save(ip,date,probability);

                        //System.out.println("add record");
                    }
                    else{
                        System.out.println("failed to add record:"+buf);
                    }
                }
            }

            //close
            //System.out.println("close client");
            br.close();
            pw.close();
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
