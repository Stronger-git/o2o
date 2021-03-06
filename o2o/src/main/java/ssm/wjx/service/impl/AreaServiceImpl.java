package ssm.wjx.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssm.wjx.cache.JedisUtil;
import ssm.wjx.dao.AreaDao;
import ssm.wjx.entity.Area;
import ssm.wjx.service.AreaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {

    private static Logger loger = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    /**
     * 引入缓存技术
     * @date 2020 3/13 9:59
     * @return
     */
    @Transactional
    @Override
    public List<Area> getAreaList() {
        String key = AREAS_KEY;
        List<Area> areas = null;
        ObjectMapper objectMapper = null;
        if (!jedisKeys.exists(key)) {
            objectMapper = new ObjectMapper();
            areas = areaDao.selectAll();
            if (areas != null && areas.size() != 0) {
                String value = null;
                try {
                    value = objectMapper.writeValueAsString(areas);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    loger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                jedisStrings.expire(key, expire, value);
            }
        } else {
            objectMapper = new ObjectMapper();
            String value = jedisStrings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                areas = objectMapper.readValue(value, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                loger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return areas;
    }
}
