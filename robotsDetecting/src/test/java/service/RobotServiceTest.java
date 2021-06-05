package service;

import org.example.robot.config.AppConfig;
import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.service.RobotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RobotServiceTest {
    @Autowired
    RobotService robotService;

    @Test
    public void getCertainRobotsTest1(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.ATTACKER)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest2(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.SPIDER)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest3(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.COMPETITOR)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest4(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.CLICKFARMER)){
            System.out.println(s);
        }
    }
}
