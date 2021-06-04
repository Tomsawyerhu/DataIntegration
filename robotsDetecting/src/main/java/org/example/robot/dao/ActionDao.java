package org.example.robot.dao;

import org.example.robot.model.ActionInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActionDao {
    List<ActionInfo> findActionsByUserId(String userid);
    List<String> findDistinctUserId();
}
