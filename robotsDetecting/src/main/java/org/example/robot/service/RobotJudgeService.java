package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.strategy.AbstractRobotJudge;
import org.example.robot.strategy.RobotJudgeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RobotJudgeService {
    @Autowired
    ActionDao actionDao;

    @Autowired
    LoginDao loginDao;

    public boolean isCertainRobotByUserId(String userId, RobotTypeEnums robotType){
        AbstractRobotJudge judge= RobotJudgeFactory.getRobotJudgeByType(robotType);
        List<ActionInfo> actionInfos=actionDao.findActionsByUserId(userId);
        List<LoginInfo> loginInfos=loginDao.findLoginsByUserId(userId);
        return judge.isRobot(loginInfos,actionInfos);
    }

    public boolean isCertainRobotByIp(String ip, RobotTypeEnums robotType){
        AbstractRobotJudge judge= RobotJudgeFactory.getRobotJudgeByType(robotType);
        List<LoginInfo> loginInfos=loginDao.findLoginsByIp(ip);
        List<ActionInfo> actionInfos=new ArrayList<>();
        Set<String> sessionIds=new HashSet<>();
        for(LoginInfo loginInfo:loginInfos){
            if(loginInfo.isSuccess()&&!sessionIds.contains(loginInfo.getSessionId())){
                actionInfos.addAll(actionDao.findActionsBySessionId(loginInfo.getSessionId()));
                sessionIds.add(loginInfo.getSessionId());
            }
        }
        return judge.isRobot(loginInfos,actionInfos);
    }
}
