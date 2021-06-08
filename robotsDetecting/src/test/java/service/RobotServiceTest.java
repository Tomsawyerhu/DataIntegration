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
        for(String s:robotService.getCertainRobots(RobotTypeEnums.ATTACKER,false)){
            System.out.println(s);
        }
    }

    @Test
    public void concurrentGetCertainRobotsTest1(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.ATTACKER,true)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest2(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.SPIDER,false)){
            System.out.println(s);
        }
    }

    @Test
    public void concurrentGetCertainRobotsTest2(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.SPIDER,true)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest3(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.COMPETITOR,false)){
            System.out.println(s);
        }
    }

    @Test
    public void concurrentGetCertainRobotsTest3(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.COMPETITOR,true)){
            System.out.println(s);
        }
    }

    @Test
    public void getCertainRobotsTest4(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.CLICKFARMER,false)){
            System.out.println(s);
        }
    }

    @Test
    public void concurrentGetCertainRobotsTest4(){
        for(String s:robotService.getCertainRobots(RobotTypeEnums.CLICKFARMER,true)){
            System.out.println(s);
        }
    }

    @Test
    public void getInfoByIpTest(){
        System.out.println(robotService.getInfoByIp("123.235.115.81"));
    }

    @Test
    public void getInfoByUserIdTest(){
        System.out.println(robotService.getInfoByUserId("751930"));
    }
}
