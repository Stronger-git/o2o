package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.User;
import ssm.wjx.entity.WeChatAuth;

import java.util.Date;

/**
 * @author WuChangJian
 * @date 2020/2/26 20:43
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeChatAuthDaoTest extends BaseTest {
    @Autowired
    private WeChatAuthDao weChatAuthDao;
    @Test
    public void testAInsert() {
        User user = new User();
        user.setUserId(2L);
        WeChatAuth weChatAuth = new WeChatAuth();
        weChatAuth.setOpenId("08qw8e9as0as0jkl");
        weChatAuth.setUser(user);
        weChatAuth.setCreateTime(new Date());
        int i = weChatAuthDao.insertWeChatAuth(weChatAuth);
        Assert.assertEquals(1, i);
    }
    @Test
    public void testBSelect() {
        WeChatAuth weChatAuth = weChatAuthDao.selectByOpenId("08qw8e9as0as0jkl");
        System.out.println(weChatAuth.getUser().getName());
    }

}
