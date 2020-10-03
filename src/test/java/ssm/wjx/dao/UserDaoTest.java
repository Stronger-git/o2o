package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.PersonInfo;

import java.util.Date;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testAInsertUser() {
        PersonInfo user = new PersonInfo();
        user.setName("勇者");
        user.setGender("男");
        user.setProfileImg("url:img");
        user.setUserType(1);
        user.setEnableStatus(1);
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        int i = userDao.insertUser(user);
        Assert.assertEquals(1, i);
    }
    @Test
    public void testBSelect() {
        PersonInfo user = userDao.selectById(2L);
        Assert.assertNotNull(user);
    }
}
