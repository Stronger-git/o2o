package ssm.wjx.dao;

import ssm.wjx.entity.PersonInfo;

public interface UserDao {
    PersonInfo selectById(Long id);
    int insertUser(PersonInfo user);
}
