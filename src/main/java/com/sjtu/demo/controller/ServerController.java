package com.sjtu.demo.controller;

import com.sjtu.demo.dao.PredictionDao;
import com.sjtu.demo.dao.RecordDao;
import com.sjtu.demo.dao.ServerDao;
import com.sjtu.demo.entities.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class ServerController {
    @Autowired
    ServerDao serverDao;
    @Autowired
    RecordDao recordDao;
    @Autowired
    PredictionDao predictionDao;

    @GetMapping(value = "/main")
    public String serverFailure(Model model, HttpSession session){
        Date date=new Date(System.currentTimeMillis());
        String ip=session.getAttribute("ip").toString();
        float[] probability=predictionDao.get7Probability(ip,date);
        ArrayList<String > ipList=serverDao.getIpFromUser(session.getAttribute("loginUser").toString());
        String ceCount=String.valueOf(recordDao.getCE(ip,date));
        String bankID=recordDao.getBankID(ip,date);
        model.addAttribute("CECount",ceCount);
        model.addAttribute("bankID",bankID);
        Server server=serverDao.getInfo(ip);
        model.addAttribute("info",server);
        model.addAttribute("failure0",probability[0]);
        model.addAttribute("failure1",probability[1]);
        model.addAttribute("failure2",probability[2]);
        model.addAttribute("failure3",probability[3]);
        model.addAttribute("failure4",probability[4]);
        model.addAttribute("failure5",probability[5]);
        model.addAttribute("failure6",probability[6]);
        model.addAttribute("ipList",ipList);
        model.addAttribute("ip",ip);
        return "memoryFailure";
    }
    @PostMapping(value = "/newIp")
    public String newIp(@RequestParam("ip") String ip, HttpSession session){
        session.setAttribute("ip",ip);
        return "redirect:/main.html";
    }


    @GetMapping(value = "/readBandwidth")
    public String readBandwidth(Model model, HttpSession session){
        Date date=new Date(System.currentTimeMillis());
        float[] values=recordDao.get7ReadBandwidth(session.getAttribute("ip").toString(),date);
        model.addAttribute("readBandwidth0",values[0]);
        model.addAttribute("readBandwidth1",values[1]);
        model.addAttribute("readBandwidth2",values[2]);
        model.addAttribute("readBandwidth3",values[3]);
        model.addAttribute("readBandwidth4",values[4]);
        model.addAttribute("readBandwidth5",values[5]);
        model.addAttribute("readBandwidth6",values[6]);

        ArrayList<String > ipList=serverDao.getIpFromUser(session.getAttribute("loginUser").toString());
        model.addAttribute("ipList",ipList);
        return "memoryRead";
    }

    @GetMapping(value = "/writeBandwidth")
    public String writeBandwidth(Model model, HttpSession session){
        Date date=new Date(System.currentTimeMillis());
        float[] values=recordDao.get7WriteBandwidth(session.getAttribute("ip").toString(),date);
        model.addAttribute("writeBandwidth0",values[0]);
        model.addAttribute("writeBandwidth1",values[1]);
        model.addAttribute("writeBandwidth2",values[2]);
        model.addAttribute("writeBandwidth3",values[3]);
        model.addAttribute("writeBandwidth4",values[4]);
        model.addAttribute("writeBandwidth5",values[5]);
        model.addAttribute("writeBandwidth6",values[6]);

        ArrayList<String > ipList=serverDao.getIpFromUser(session.getAttribute("loginUser").toString());
        model.addAttribute("ipList",ipList);
        return "memoryWrite";
    }

    @GetMapping(value = "/readLatency")
    public String readLatency(Model model, HttpSession session){
        Date date=new Date(System.currentTimeMillis());
        float[] values=recordDao.get7ReadLatency(session.getAttribute("ip").toString(),date);
        model.addAttribute("readLatency0",values[0]);
        model.addAttribute("readLatency1",values[1]);
        model.addAttribute("readLatency2",values[2]);
        model.addAttribute("readLatency3",values[3]);
        model.addAttribute("readLatency4",values[4]);
        model.addAttribute("readLatency5",values[5]);
        model.addAttribute("readLatency6",values[6]);

        ArrayList<String > ipList=serverDao.getIpFromUser(session.getAttribute("loginUser").toString());
        model.addAttribute("ipList",ipList);
        return "memoryLatency";
    }
}
