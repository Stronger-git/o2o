package ssm.wjx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.wjx.dao.UserDao;
import ssm.wjx.entity.User;
import ssm.wjx.service.UserService;

/**
 * @author WuChangJian
 * @date 2020/2/26 21:37
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.selectById(id);
    }
}
