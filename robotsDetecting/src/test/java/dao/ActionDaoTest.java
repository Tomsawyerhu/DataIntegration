package dao;

import org.example.robot.config.AppConfig;
import org.example.robot.dao.ActionDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ActionDaoTest {
    @Autowired
    ActionDao actionDao;

    @Test
    public void findActionsByUserIdTest(){
        String userId="367135";
        System.out.println(actionDao.findActionsByUserId(userId).size());
    }

    @Test
    public void findActionsBySessionIdTest(){
        String sessionId="000000";
        System.out.println(actionDao.findActionsBySessionId(sessionId).size());
    }

}
