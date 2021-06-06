package org.example.robot.controller;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.service.RobotJudgeService;
import org.example.robot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RobotController {
    @Autowired
    RobotService robotService;
    @Autowired
    RobotJudgeService robotJudgeService;

    @GetMapping("/attackers")
    public List<String> detectAttackers(){
        return robotService.getCertainRobots(RobotTypeEnums.ATTACKER);
    }

    @GetMapping("/spiders")
    public List<String> detectSpiders(){
        return robotService.getCertainRobots(RobotTypeEnums.SPIDER);
    }

    @GetMapping("/clickfarmers")
    public List<String> detectClickFarmers(){
        return robotService.getCertainRobots(RobotTypeEnums.CLICKFARMER);
    }

    @GetMapping("/competitors")
    public List<String> detectOrderCompetitors(){
        return robotService.getCertainRobots(RobotTypeEnums.COMPETITOR);
    }

    @GetMapping("/users")
    public List<String> distinctUser(){
        return new ArrayList<>(robotService.getDistinctLoginUsers());
    }

    @GetMapping("/ips")
    public List<String> distinctIp(){
        return new ArrayList<>(robotService.getDistinctLoginIp());
    }

    @GetMapping("/isrobot/userid/{userid}")
    public int isRobotByUserId(@PathVariable(name = "userid")String userid){
        if(robotJudgeService.isCertainRobotByUserId(userid,RobotTypeEnums.CLICKFARMER)) {return 3;}
        else if(robotJudgeService.isCertainRobotByUserId(userid,RobotTypeEnums.COMPETITOR)){return 4;}
        else{return -1;}
    }

    @GetMapping("/isrobot/ip/{ip}")
    public int isRobotByIp(@PathVariable(name = "ip")String ip){
        if(robotJudgeService.isCertainRobotByIp(ip,RobotTypeEnums.ATTACKER)){
            return 1;
        }else if(robotJudgeService.isCertainRobotByIp(ip,RobotTypeEnums.SPIDER)){
            return 2;
        }else{return -1;}
    }
}
