package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.ShopCategory;

import java.util.List;

/**
 * Created by WuJiXian on 2020/9/6 17:17
 */
public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Test
    public void testTwoLevel() {
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(2L);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setParent(parent);
        List<ShopCategory> shopCategories = shopCategoryDao.selectByShopCategory(shopCategory);
        Assert.assertEquals(2, shopCategories.size());
    }
}
