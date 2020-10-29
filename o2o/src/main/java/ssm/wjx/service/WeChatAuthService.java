package ssm.wjx.service;

import ssm.wjx.dto.WeChatAuthExecution;
import ssm.wjx.entity.WeChatAuth;

/**
 * @author WuChangJian
 * @date 2020/2/26 21:04
 */
public interface WeChatAuthService {
    WeChatAuth getByOpenId(String openId);
    WeChatAuthExecution addWeChatAuth(WeChatAuth weChatAuth);
}
