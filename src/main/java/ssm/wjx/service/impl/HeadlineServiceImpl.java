package ssm.wjx.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.wjx.cache.JedisUtil;
import ssm.wjx.dao.HeadlineDao;
import ssm.wjx.entity.Headline;
import ssm.wjx.service.HeadlineService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class HeadlineServiceImpl implements HeadlineService {
    @Autowired
    private HeadlineDao headlineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    public List<Headline> getHeadlineList(Headline headlineCondition) {
        String key = HEADLINE_KEY;
        List<Headline> headlines = null;
        ObjectMapper mapper = null;
        if (headlineCondition != null && headlineCondition.getEnableStatus() != null) {
            key = key + headlineCondition.getEnableStatus();
        }
        if (!jedisKeys.exists(key)) {
            mapper = new ObjectMapper();
            headlines = headlineDao.selectByHeadline(headlineCondition);
            String value = null;
            if (headlines != null && headlines.size() != 0) {
                try {
                    value = mapper.writeValueAsString(headlines);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                jedisStrings.set(key, value);
            }
        } else {
            mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Headline.class);
            String value = jedisStrings.get(key);
            try {
                headlines = mapper.readValue(value, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return headlines;
    }
}
