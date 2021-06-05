package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.utils.TimeFormatTransformer;

import java.util.List;

public class CompetitorRobotJudge extends AbstractRobotJudge {
    protected RobotTypeEnums getType() {
        return RobotTypeEnums.COMPETITOR;
    }

    public boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos) {
        //整点购物占比率
        float rate=0.5f;
        int sum=0;
        int clockTimes=0;
        for(ActionInfo actionInfo:actionInfos){
            if(actionInfo.isBuyAction()){
                sum+=1;
                Long s=TimeFormatTransformer.formatTimeStringToTimeStamp(actionInfo.getActionTime());
                if(s!=null&&s%3600000<=59){clockTimes+=1;}
            }
        }
        return (float)clockTimes/(float)sum >rate;
    }
}
