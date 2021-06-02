package dao;

import org.example.robot.config.AppConfig;
import org.example.robot.dao.LoginDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LoginDaoTest {
    @Autowired
    LoginDao loginDao;

    @Test
    public void findLoginsByUserIdTest(){
        String userId="912924";
        System.out.println(loginDao.findLoginsByUserId(userId).size());
    }

}
