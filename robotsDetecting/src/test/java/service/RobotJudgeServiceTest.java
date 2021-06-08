package service;

import org.example.robot.config.AppConfig;
import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.service.RobotJudgeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RobotJudgeServiceTest {
    @Autowired
    RobotJudgeService judgeService;

    @Test
    public void isCertainRobotByUserIdTest1(){
        String userId="912924";
        System.out.println(judgeService.isCertainRobotByUserId(userId, RobotTypeEnums.CLICKFARMER));
    }

    @Test
    public void isCertainRobotByUserIdTest2(){
        String userId="912924";
        System.out.println(judgeService.isCertainRobotByUserId(userId, RobotTypeEnums.COMPETITOR));
    }


    @Test
    public void isCertainRobotByIpTest1(){
        String ip="139.210.43.58";
        System.out.println(judgeService.isCertainRobotByIp(ip, RobotTypeEnums.ATTACKER));
    }

    @Test
    public void isCertainRobotByIpTest2(){
        String ip="139.210.43.58";
        System.out.println(judgeService.isCertainRobotByIp(ip, RobotTypeEnums.SPIDER));
    }
}
