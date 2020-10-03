package ssm.wjx.service;

import ssm.wjx.entity.PersonInfo;

/**
 * @author WuChangJian
 * @date 2020/2/26 21:35
 */
public interface UserService {
    PersonInfo getUserById(Long id);
}
