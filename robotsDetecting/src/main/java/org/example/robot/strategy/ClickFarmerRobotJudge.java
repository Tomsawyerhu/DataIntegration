package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.utils.TimeFormatTransformer;

import java.util.*;

public class ClickFarmerRobotJudge extends AbstractRobotJudge {
    protected RobotTypeEnums getType() {
        return RobotTypeEnums.CLICKFARMER;
    }

    public boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos) {
        //在一定时间内重复购买达到一定次数
        long timeLimit=60000;
        int numLimit=5;
        Map<String,List<Long>> actionGroups=new HashMap<>();
        for(ActionInfo actionInfo:actionInfos){
            if(actionInfo.isBuyAction()) {
                String targetKey = actionInfo.getCategoryId() + actionInfo.getItemId();
                if (actionGroups.containsKey(targetKey)) {
                    List<Long> l = actionGroups.get(targetKey);
                    l.add(TimeFormatTransformer.formatTimeStringToTimeStamp(actionInfo.getActionTime()));
                } else {
                    List<Long> l = new ArrayList<>();
                    l.add(TimeFormatTransformer.formatTimeStringToTimeStamp(actionInfo.getActionTime()));
                    actionGroups.put(targetKey, l);
                }
            }
        }

        for(List<Long> l:actionGroups.values()){
            l.sort(Long::compareTo);
            for(int i=0;i<=l.size()-numLimit;i++){
                if(Math.abs(l.get(i+numLimit-1)-l.get(i))<timeLimit) return true;
            }
        }

        return false;
    }
}
