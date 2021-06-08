package org.example.robot.service;

import org.example.robot.constants.RobotTypeEnums;
import org.example.robot.dao.ActionDao;
import org.example.robot.dao.LoginDao;
import org.example.robot.model.ActionInfo;
import org.example.robot.model.LoginInfo;
import org.example.robot.strategy.AbstractRobotJudge;
import org.example.robot.strategy.RobotJudgeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Service
public class RobotJudgeService {
    @Autowired
    ActionDao actionDao;

    @Autowired
    LoginDao loginDao;

    private final ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(8, 8, 0L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
    private final ConcurrentHashMap<String,Future<Boolean>> saving1=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Future<Boolean>> saving2=new ConcurrentHashMap<>();

    public boolean isCertainRobotByUserId(String userId, RobotTypeEnums robotType){
        AbstractRobotJudge judge= RobotJudgeFactory.getRobotJudgeByType(robotType);
        List<ActionInfo> actionInfos=actionDao.findActionsByUserId(userId);
        List<LoginInfo> loginInfos=loginDao.findLoginsByUserId(userId);
        return judge.isRobot(loginInfos,actionInfos);
    }

    public void concurrentIsCertainRobotByUserId(String userId, RobotTypeEnums robotType){
        Callable<Boolean> task= () -> isCertainRobotByUserId(userId,robotType);
        this.saving1.put(userId,this.threadPoolExecutor.submit(task));
    }

    public boolean isCertainRobotByIp(String ip, RobotTypeEnums robotType){
        AbstractRobotJudge judge= RobotJudgeFactory.getRobotJudgeByType(robotType);
        List<LoginInfo> loginInfos=loginDao.findLoginsByIp(ip);
        List<ActionInfo> actionInfos=new ArrayList<>();
        Set<String> sessionIds=new HashSet<>();
        for(LoginInfo loginInfo:loginInfos){
            if(loginInfo.isSuccess()&&!sessionIds.contains(loginInfo.getSessionId())){
                actionInfos.addAll(actionDao.findActionsBySessionId(loginInfo.getSessionId()));
                sessionIds.add(loginInfo.getSessionId());
            }
        }
        return judge.isRobot(loginInfos,actionInfos);
    }

    public void concurrentIsCertainRobotByIp(String ip, RobotTypeEnums robotType){
        Callable<Boolean> task= () -> isCertainRobotByIp(ip,robotType);
        this.saving2.put(ip,this.threadPoolExecutor.submit(task));
    }

    protected List<String> saving1Results(){
        List<String> list=new ArrayList<>();
        saving1.forEach((k,f)->{
            try {
                if(f.get()){
                    list.add(k);
                    System.out.println(k);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return list;
    }


    protected List<String> saving1ResultsAndClear(){
        List<String> list=saving1Results();
        saving1.clear();
        return list;
    }

    protected List<String> saving2Results(){
        List<String> list=new ArrayList<>();
        saving2.forEach((k,f)->{
            try {
                if(f.get()){
                    list.add(k);
                    System.out.println(k);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return list;
    }


    protected List<String> saving2ResultsAndClear(){
        List<String> list=saving2Results();
        saving2.clear();
        return list;
    }
}
