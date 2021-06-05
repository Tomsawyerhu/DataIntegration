package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.utils.TimeFormatTransformer;

import java.util.ArrayList;
import java.util.List;

public class SpiderRobotJudge extends AbstractRobotJudge {
    protected RobotTypeEnums getType() {
        return RobotTypeEnums.SPIDER;
    }

    public boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos) {
        //浏览速率超过阈值
        long minLeak=1000;
        List<Long> timestamps= new ArrayList<>();
        for(ActionInfo actionInfo:actionInfos) {
            if (actionInfo.isGetDetailAction()) {
                timestamps.add(TimeFormatTransformer.formatTimeStringToTimeStamp(actionInfo.getActionTime()));
            }
        }
        timestamps.sort(Long::compareTo);
        for(int i=1;i<timestamps.size();i+=1){
            if(Math.abs(timestamps.get(i)-timestamps.get(i-1))<minLeak) return true;
        }
        return false;
    }
}
