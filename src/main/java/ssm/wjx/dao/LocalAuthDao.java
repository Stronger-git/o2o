package ssm.wjx.dao;

import org.apache.ibatis.annotations.Param;
import ssm.wjx.entity.LocalAuth;

import java.util.Date;

/**
 * @author WuChangJian
 * @date 2020/3/17 14:48
 */
public interface LocalAuthDao {
    LocalAuth selectByUsernameAndPwd(@Param("username")String username, @Param("password")String password);
    LocalAuth selectByUserId(Long userId);
    int insert(LocalAuth localAuth);
    int updateUsernameAndPwd(@Param("userId")Long userId, @Param("password")String password, @Param("newPassword")String newPassword,@Param("updateTime") Date updateTime);
}
