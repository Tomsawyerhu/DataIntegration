package org.example.robot.controller;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RobotController {
    @Autowired
    RobotService robotService;

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
}
