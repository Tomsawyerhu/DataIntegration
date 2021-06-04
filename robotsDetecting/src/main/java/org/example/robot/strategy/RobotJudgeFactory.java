package org.example.robot.strategy;

import org.example.robot.constants.RobotTypeEnums;

import java.util.ArrayList;
import java.util.List;

public class RobotJudgeFactory {
    private static final int robotTypeNum=4;
    private static final List<AbstractRobotJudge> judgeList=new ArrayList<AbstractRobotJudge>(robotTypeNum);

    static {
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
