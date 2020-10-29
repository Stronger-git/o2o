package ssm.wjx.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.ProductCategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao proCatDao;

    @Test
    public void testDeleteById() {
        int i = proCatDao.deleteById(10L, 19L);
        int j = proCatDao.deleteById(11L, 19L);
    }

    @Test
    public void testInsertBatch() {
        List<ProductCategory> list = new ArrayList<>();
        ProductCategory pc1 = new ProductCategory();
        pc1.setCreateTime(new Date());
        pc1.setPriority(23);
        pc1.setProductCategoryName("产品类别ONE");
        pc1.setShopId(19L);
        ProductCategory pc2 = new ProductCategory();
        pc2.setCreateTime(new Date());
        pc2.setPriority(24);
        pc2.setProductCategoryName("产品类别TWO");
        pc2.setShopId(19L);
        list.add(pc1);
        list.add(pc2);
        proCatDao.insertBatch(list);
    }

}
