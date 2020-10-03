package ssm.wjx.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ssm.wjx.BaseTest;
import ssm.wjx.dto.ImageHolder;
import ssm.wjx.dto.ShopExecution;
import ssm.wjx.entity.Area;
import ssm.wjx.entity.Shop;
import ssm.wjx.entity.ShopCategory;
import ssm.wjx.entity.PersonInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() {
        PersonInfo user = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(4L);
        user.setUserId(1L);
        Shop shop = new Shop();
        shop.setArea(area);
        shop.setOwner(user);
        shop.setShopCategory(shopCategory);
        shop.setShopName("影院五");
        shop.setShopDesc("测试");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setAdvice("审核中");
        File file = new File("D:\\FILE\\dog.jpg");
        try {
            InputStream in = new FileInputStream(file);
            shopService.addShop(shop, new ImageHolder(file.getName(), in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Ignore
    public void testGetShopById() {
        ShopExecution se = shopService.getShopById(1L);
        Assert.assertEquals(1, se.getState());
    }

    @Test
    @Ignore
    public void testModifyShop() {
        Area area = new Area();
        area.setAreaId(1);
        ShopExecution se = shopService.getShopById(18L);
        Shop shop = se.getShop();
        shop.setArea(area);

        File file = new File("D:\\FILE\\test.jpg");
        try {
            InputStream in = new FileInputStream(file);
            ShopExecution shopExecution = shopService.modifyShop(shop, new ImageHolder(file.getName(), in));
            System.out.println(shopExecution.getStateInfo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetShopList() {
        PersonInfo user = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        Area area = new Area();
        area.setAreaId(2);
        shopCategory.setShopCategoryId(4L);
        user.setUserId(1L);
        Shop shop = new Shop();
        shop.setOwner(user);
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        ShopExecution se = shopService.getShopList(shop, 2, 3);
        System.out.println(se.getShopList().size());
        System.out.println(se.getCount());
    }
}
