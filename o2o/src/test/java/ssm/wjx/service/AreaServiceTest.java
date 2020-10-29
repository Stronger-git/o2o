package ssm.wjx.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.Area;

import java.util.List;

/**
 * @author WuChangJian
 * @date 2020/3/13 10:36
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        Assert.assertNotNull(areaList);
    }
}
