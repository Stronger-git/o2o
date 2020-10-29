package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.Area;
import ssm.wjx.entity.Shop;
import ssm.wjx.entity.ShopCategory;
import ssm.wjx.entity.PersonInfo;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testInsertShop() {
        PersonInfo user = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        user.setUserId(1L);
        Shop shop = new Shop();
        shop.setOwner(user);
        shop.setShopCategory(shopCategory);
        shop.setShopName("快餐");
        shop.setShopDesc("油条大包子");
        shop.setShopAddr("test");
        shop.setCreateTime(new Date());
        shop.setPhone("test");
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shopDao.insertShop(shop);
    }
    @Test
    @Ignore
    public void testUpdateShop() {
        Area area = new Area();
        area.setAreaId(1);
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setArea(area);
        shop.setShopName("快餐");
        shop.setShopDesc("豆浆小米粥");
        int rows = shopDao.updateShop(shop);
        Assert.assertEquals(1, rows);
    }
    @Test
    public void testSelectAllByShop() {

        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1L);
        Area area = new Area();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(9L);
        shopCategory.setParent(parent);
        Shop shop = new Shop();
        shop.setShopCategory(shopCategory);
//        shop.setArea(area);
        List<Shop> shops1 = shopDao.selectAllByShop(shop, 0, 9);
        System.out.println(shops1.size());
        Long shops = shopDao.selectCountByShop(shop);
        System.out.println(shops);
    }
}
