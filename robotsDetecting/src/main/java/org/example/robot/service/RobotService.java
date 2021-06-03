package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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


    public int getCertainRobotsNum(RobotTypeEnums robotType){
        Set<String> users=null;
        if(robotType==RobotTypeEnums.SPIDER){users=getDistinctLoginUsers();}
        else{users=getDistinctActionUsers();}
        int count=0;
        for(String user:users){
            if(judgeService.isCertainRobot(user,robotType)){count+=1;}
        }
        return count;
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
}
