package ssm.wjx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.wjx.cache.JedisUtil;
import ssm.wjx.service.CacheService;

import java.util.Set;

/**
 * @author WuChangJian
 * @date 2020/3/13 15:03
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKey;
    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> set = jedisKey.match(keyPrefix);
        for (String s : set) {
            jedisKey.del(s);
        }
    }
}
