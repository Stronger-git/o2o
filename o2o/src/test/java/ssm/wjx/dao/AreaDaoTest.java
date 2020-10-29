package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.Area;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testSelectAll() {
        List<Area> areas = areaDao.selectAll();
        Assert.assertEquals(2, areas.size());
    }

}
