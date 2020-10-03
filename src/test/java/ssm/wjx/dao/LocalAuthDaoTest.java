package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.LocalAuth;
import ssm.wjx.entity.PersonInfo;

import java.util.Date;

/**
 * @author WuChangJian
 * @date 2020/3/17 15:45
 */
public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    LocalAuthDao authDao;
    @Test
    public void testInsert() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo user = new PersonInfo();
        user.setUserType(1);
        user.setUserId(1L);
        localAuth.setUser(user);
        localAuth.setUsername("Git");
        localAuth.setPassword("123");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int insert = authDao.insert(localAuth);
        Assert.assertEquals(1, insert);
        System.out.println(localAuth.getLocalAuthId());
    }

    @Test
    public void testSelectByUsernameAndPwd() {
        LocalAuth localAuth = authDao.selectByUsernameAndPwd("Git", "123");
        PersonInfo user = localAuth.getUser();
        Assert.assertEquals("测试", user.getName());
    }

    @Test
    public void testSelectByUserId() {
        LocalAuth localAuth = authDao.selectByUserId(2L);
        Assert.assertNull(localAuth);
    }

    @Test
    public void updateUsernameAndPwd() {
        int i = authDao.updateUsernameAndPwd(1L, "123", "321", new Date());
        Assert.assertEquals(1, i);
        LocalAuth localAuth = authDao.selectByUsernameAndPwd("Git", "321");
        Assert.assertEquals("321", localAuth.getPassword());
    }

}
