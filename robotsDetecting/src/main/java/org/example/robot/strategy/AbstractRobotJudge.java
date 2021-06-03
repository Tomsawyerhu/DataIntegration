package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRobotJudge {
    private static final int robotTypeNum=4;
    private static List<AbstractRobotJudge> judgeList;

    protected abstract  RobotTypeEnums getType();

    public abstract boolean isRobot(List<LoginInfo> loginInfos, List<ActionInfo> actionInfos);

    public AbstractRobotJudge(){
        judgeList=new ArrayList<AbstractRobotJudge>(robotTypeNum);
        judgeList.add(new AttackRobotJudge());
        judgeList.add(new ClickFarmerRobotJudge());
        judgeList.add(new CompetitorRobotJudge());
        judgeList.add(new SpiderRobotJudge());
    }

    public static AbstractRobotJudge getRobotJudgeByType(RobotTypeEnums robotType){
        AbstractRobotJudge judge=null;
        for(AbstractRobotJudge robotJudge:judgeList){
            if(robotJudge.getType()==robotType){
                judge=robotJudge;
            }
        }
        return judge;
    }
}
