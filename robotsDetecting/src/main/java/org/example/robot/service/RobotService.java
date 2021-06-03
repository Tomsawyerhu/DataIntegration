package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RobotService {

    final RobotJudgeService judgeService;

    private Set<String> distinctUsers=null;

    @Autowired
    public RobotService(RobotJudgeService judgeService) {
        this.judgeService = judgeService;
    }

    public int getCertainRobotsNum(RobotTypeEnums robotType){
        Set<String> users=getDistinctUsers();
        int count=0;
        for(String user:users){
            if(judgeService.isCertainRobot(user,robotType)){count+=1;}
        }
        return count;
    }

    private Set<String> getDistinctUsers(){
        if(distinctUsers!=null){return distinctUsers;}
        else{
            //todo 查数据库 缓存
            return null;
        }
    }
}
