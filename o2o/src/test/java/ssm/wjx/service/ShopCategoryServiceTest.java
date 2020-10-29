package ssm.wjx.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.ShopCategory;

import java.util.List;

public class ShopCategoryServiceTest extends BaseTest {
    @Autowired
    private ShopCategoryService categoryService;
    @Test
    public void testGetCategoryList() {

        List<ShopCategory> shopCategoryList = categoryService.getShopCategoryList(null);
        for (ShopCategory shopCategory : shopCategoryList) {
            System.out.println(shopCategory.getShopCategoryDesc());
        }
    }


}
