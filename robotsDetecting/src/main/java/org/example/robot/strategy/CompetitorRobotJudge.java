package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;

import java.util.List;

public class CompetitorRobotJudge extends AbstractRobotJudge {
    protected RobotTypeEnums getType() {
        return RobotTypeEnums.COMPETITOR;
    }

    public boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos) {
        //todo
        return false;
    }
}
