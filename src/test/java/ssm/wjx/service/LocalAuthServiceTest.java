package ssm.wjx.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.dto.LocalAuthExecution;
import ssm.wjx.entity.LocalAuth;
import ssm.wjx.entity.PersonInfo;

import java.util.Date;

/**
 * @author WuChangJian
 * @date 2020/3/17 19:46
 */
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    LocalAuthService authService;

    @Test
    public void testBindLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        PersonInfo user = new PersonInfo();
        user.setUserType(1);
        user.setUserId(2L);
        localAuth.setUser(user);
        localAuth.setUsername("Stronger-Git");
        localAuth.setPassword("123");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        LocalAuthExecution localAuthExecution = authService.bindLocalAuth(localAuth);
        System.out.println(localAuthExecution.getStateInfo());
    }

    @Test
    public void testModifyLocalAuth() {
        LocalAuthExecution localAuthExecution = authService.modifyLocalAuth(2L, "321", "123");
        System.out.println(localAuthExecution.getStateInfo());
    }


}
