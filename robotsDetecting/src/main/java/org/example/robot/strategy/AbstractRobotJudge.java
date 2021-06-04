package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;

import java.util.List;

public abstract class AbstractRobotJudge {
    protected abstract  RobotTypeEnums getType();
    public abstract boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos);
}
