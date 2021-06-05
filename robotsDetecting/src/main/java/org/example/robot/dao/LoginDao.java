package org.example.robot.dao;

import org.example.robot.model.LoginInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LoginDao {
    List<LoginInfo> findLoginsByUserId(String userid);
    List<String> findDistinctUserId();
    List<LoginInfo> findLoginsByIp(String ip);
    List<String> findDistinctIp();
}
