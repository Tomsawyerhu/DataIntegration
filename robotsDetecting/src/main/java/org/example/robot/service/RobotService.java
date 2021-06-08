package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.model.UniformInfo;
import org.example.robot.utils.TimeFormatTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RobotService {

    @Autowired
    private RobotJudgeService judgeService;

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private ActionDao actionDao;

    private Set<String> distinctLoginUsers=null;
    private Set<String> distinctLoginIp=null;


    public List<String> getCertainRobots(RobotTypeEnums robotType,boolean concurrent){
        Set<String> users=null;
        Set<String> ips=null;
        boolean flag=false;
        if(robotType==RobotTypeEnums.ATTACKER||robotType==RobotTypeEnums.SPIDER){
            ips=getDistinctLoginIp();
        }
        else{
            users=getDistinctLoginUsers();
            flag=true;
        }
        List<String> robotsInfo= new ArrayList<>();
        if(flag){
            for(String user:users){
                if(concurrent){
                    judgeService.concurrentIsCertainRobotByUserId(user,robotType);
                }else{
                    if(judgeService.isCertainRobotByUserId(user,robotType)){
                        robotsInfo.add(user);
                    }
                }
            }
        }else{
            for(String ip:ips){
                if(concurrent){
                    judgeService.concurrentIsCertainRobotByIp(ip,robotType);
                }else{
                    if(judgeService.isCertainRobotByIp(ip,robotType)){
                        robotsInfo.add(ip);
                    }
                }
            }
        }
        if(concurrent){
            if(flag){return judgeService.saving1ResultsAndClear();}
            else{return judgeService.saving2ResultsAndClear();}
        }else{
            return robotsInfo;
        }
    }

    public String getInfoByIp(String ip){
        List<LoginInfo> loginInfos=loginDao.findLoginsByIp(ip);
        List<ActionInfo> actionInfos=new ArrayList<>();
        Set<String> sessionIds=new HashSet<>();
        for(LoginInfo loginInfo:loginInfos){
            if(loginInfo.isSuccess()&&!sessionIds.contains(loginInfo.getSessionId())){
                actionInfos.addAll(actionDao.findActionsBySessionId(loginInfo.getSessionId()));
                sessionIds.add(loginInfo.getSessionId());
            }
        }
        return getString(actionInfos, loginInfos);
    }

    public String getInfoByUserId(String userId){
        List<ActionInfo> actionInfos=actionDao.findActionsByUserId(userId);
        List<LoginInfo> loginInfos=loginDao.findLoginsByUserId(userId);
        return getString(actionInfos, loginInfos);
    }

    private String getString(List<ActionInfo> actionInfos, List<LoginInfo> loginInfos) {
        loginInfos.sort((LoginInfo o1, LoginInfo o2) -> TimeFormatTransformer.formatTimeStringToTimeStamp(o1.getLoginTime()) < TimeFormatTransformer.formatTimeStringToTimeStamp(o2.getLoginTime()) ? 1 : -1);
        actionInfos.sort((ActionInfo o1, ActionInfo o2) -> TimeFormatTransformer.formatTimeStringToTimeStamp(o1.getActionTime()) < TimeFormatTransformer.formatTimeStringToTimeStamp(o2.getActionTime()) ? 1 : -1);
        List<UniformInfo> infos=new ArrayList<>();
        for(LoginInfo info:loginInfos){
            infos.add(UniformInfo.transform(info));
        }

        for(ActionInfo info:actionInfos){
            infos.add(UniformInfo.transform(info));
        }
        infos.sort(UniformInfo::earlierThan);
        StringBuilder builder=new StringBuilder();
        for(UniformInfo info:infos){
            builder.append(info.toString()).append("\n");
        }
        return builder.toString();
    }


    public Set<String> getDistinctLoginUsers(){
        if(distinctLoginUsers==null){
            this.distinctLoginUsers= new HashSet<>(loginDao.findDistinctUserId());
        }
        return distinctLoginUsers;
    }

    public Set<String> getDistinctLoginIp(){
        if(distinctLoginIp==null){
            this.distinctLoginIp= new HashSet<>(loginDao.findDistinctIp());
        }
        return distinctLoginIp;
    }
}
