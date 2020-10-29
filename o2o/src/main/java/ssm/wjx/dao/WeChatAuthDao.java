package ssm.wjx.dao;

import ssm.wjx.entity.WeChatAuth;

public interface WeChatAuthDao {
    WeChatAuth selectByOpenId(String id);
    int insertWeChatAuth(WeChatAuth weChatAuth);
}
