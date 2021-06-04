package org.example.robot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {
    @GetMapping("/attackers")
    public void detectAttackers(){
        //todo
    }

    @GetMapping("/spiders")
    public void detectSpiders(){
        //todo
    }

    @GetMapping("/clickfarmers")
    public void detectClickFarmers(){
        //todo
    }

    @GetMapping("/competitors")
    public void detectOrderCompetitors(){
        //todo
    }
}
