package org.example.robot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {
    @GetMapping("/attackers")
    public void detectAttackers(){}

    @GetMapping("/spiders")
    public void detectSpiders(){}

    @GetMapping("/clickfarmers")
    public void detectClickFarmers(){}

    @GetMapping("/competitors")
    public void detectOrderCompetitors(){}
}
