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

    private Set<String> distinctActionUsers=null;
    private Set<String> distinctLoginUsers=null;
    private Set<String> distinctActionIp=null;
    private Set<String> distinctLoginIp=null;


    public List<String> getCertainRobotsNum(RobotTypeEnums robotType){
        Set<String> users=null;
        Set<String> ips=null;
        boolean flag=false;
        if(robotType==RobotTypeEnums.ATTACKER){
            ips=getDistinctLoginIp();
        }
        else if(robotType==RobotTypeEnums.SPIDER){ips=getDistinctActionIp();}
        else{
            users=getDistinctActionUsers();
            flag=true;
        }
        List<String> robotsInfo=new ArrayList<String>();
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

    private Set<String> getDistinctActionUsers(){
        if(distinctActionUsers==null){
            this.distinctActionUsers=new HashSet<String>(actionDao.findDistinctUserId());
        }
        return distinctActionUsers;
    }

    private Set<String> getDistinctLoginUsers(){
        if(distinctLoginUsers==null){
            this.distinctLoginUsers= new HashSet<String>(loginDao.findDistinctUserId());
        }
        return distinctLoginUsers;
    }

    private Set<String> getDistinctActionIp(){
        if(distinctActionIp==null){
            this.distinctActionIp=new HashSet<String>(actionDao.findDistinctIp());
        }
        return distinctActionIp;
    }

    private Set<String> getDistinctLoginIp(){
        if(distinctLoginIp==null){
            this.distinctLoginIp= new HashSet<String>(loginDao.findDistinctIp());
        }
        return distinctLoginIp;
    }
}
