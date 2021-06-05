package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;

import java.util.List;

public class AttackRobotJudge extends AbstractRobotJudge {
    protected RobotTypeEnums getType() {
        return RobotTypeEnums.ATTACKER;
    }

    public boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos) {
        //登录成功率小于预定值
        float successRate=0.5f;
        int sum=loginInfos.size();
        int successTimes=0;
        for(LoginInfo loginInfo:loginInfos){
            if(loginInfo.getSuccess()==1){
                successTimes+=1;
            }
        }
        return (float)successTimes/(float)sum < successRate;
    }
}
