package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.Product;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.entity.Shop;

import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsert() {
        Shop shop = new Shop();
        ProductCategory pc = new ProductCategory();
        shop.setShopId(19L);
        pc.setProductCategoryId(2L);
        Product product = new Product();
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试产品三");
        product.setImgAddr("three");
        product.setCreateTime(new Date());
        product.setNormalPrice("100$");
        product.setPriority(5);
        product.setEnableStatus(1);
        productDao.insertProduct(product);
    }
    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setProductId(4L);
        product.setEnableStatus(4);
        product.setProductName("更新测试三");
        product.setImgAddr("three");
        product.setCreateTime(new Date());
        product.setNormalPrice("100");
        product.setPriority(5);
        product.setEnableStatus(1);
        productDao.updateProduct(product);
    }
    @Test
    public void testSelectById() {
        Product product = productDao.selectById(7L);
        Assert.assertEquals(2, product.getProductImgList().size());
    }
    @Test
    public void testSelectAllByProduct() {
        Product product = new Product();
        product.setProductName("测试");
        Shop shop = new Shop();
        shop.setShopId(19L);
        product.setShop(shop);
        List<Product> products = productDao.selectAllByProduct(product, 0, 3);

    }

}
