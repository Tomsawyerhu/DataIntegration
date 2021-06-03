package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.strategy.AbstractRobotJudge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RobotJudgeService {
    @Autowired
    ActionDao actionDao;

    @Autowired
    LoginDao loginDao;

    public boolean isCertainRobot(String userId, RobotTypeEnums robotType){
        AbstractRobotJudge judge=AbstractRobotJudge.getRobotJudgeByType(robotType);
        List<ActionInfo> actionInfos=actionDao.findActionsByUserId(userId);
        List<LoginInfo> loginInfos=loginDao.findLoginsByUserId(userId);
        return judge.isRobot(loginInfos,actionInfos);
    }
}
