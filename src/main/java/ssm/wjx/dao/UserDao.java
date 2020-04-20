package ssm.wjx.dao;

import ssm.wjx.entity.User;

public interface UserDao {
    User selectById(Long id);
    int insertUser(User user);
}
