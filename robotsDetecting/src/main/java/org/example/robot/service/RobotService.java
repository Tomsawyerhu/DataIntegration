package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RobotService {

    @Autowired
    private RobotJudgeService judgeService;

    @Autowired
    private ActionDao actionDao;
    @Autowired
    private LoginDao loginDao;

    private Set<String> distinctLoginUsers=null;
    private Set<String> distinctLoginIp=null;


    public List<String> getCertainRobots(RobotTypeEnums robotType){
        Set<String> users=null;
        Set<String> ips=null;
        boolean flag=false;
        if(robotType==RobotTypeEnums.ATTACKER||robotType==RobotTypeEnums.SPIDER){
            ips=getDistinctLoginIp();
        }
        else{
            users=getDistinctLoginUsers();
            flag=true;
        }
        List<String> robotsInfo= new ArrayList<>();
        if(flag){
            for(String user:users){
                if(judgeService.isCertainRobotByUserId(user,robotType)){robotsInfo.add(user);}
            }
        }else{
            for(String ip:ips){
                if(judgeService.isCertainRobotByIp(ip,robotType)){robotsInfo.add(ip);}
            }
        }

        return robotsInfo;
    }


    private Set<String> getDistinctLoginUsers(){
        if(distinctLoginUsers==null){
            this.distinctLoginUsers= new HashSet<String>(loginDao.findDistinctUserId());
        }
        return distinctLoginUsers;
    }

    private Set<String> getDistinctLoginIp(){
        if(distinctLoginIp==null){
            this.distinctLoginIp= new HashSet<String>(loginDao.findDistinctIp());
        }
        return distinctLoginIp;
    }
}
