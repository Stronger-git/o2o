package ssm.wjx.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.dto.WeChatAuthExecution;
import ssm.wjx.entity.PersonInfo;
import ssm.wjx.entity.WeChatAuth;

import java.util.Date;

/**
 * @author WuChangJian
 * @date 2020/2/26 21:39
 */
public class WeChatServiceTest extends BaseTest {
    @Autowired
    private WeChatAuthService weChatAuthService;
    @Test
    public void testRegister() {
        PersonInfo user = new PersonInfo();
        user.setName("勇者");
        user.setGender("男");
        user.setProfileImg("url:img");
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());

        WeChatAuth weChatAuth = new WeChatAuth();
        weChatAuth.setOpenId("08qw0as0jkl");
        weChatAuth.setUser(user);
        weChatAuth.setCreateTime(new Date());
        WeChatAuthExecution weChatAuthExecution = weChatAuthService.addWeChatAuth(weChatAuth);
        System.out.println(weChatAuthExecution.getStateInfo());
    }
}
