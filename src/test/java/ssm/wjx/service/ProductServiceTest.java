package ssm.wjx.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.dto.ImageHolder;
import ssm.wjx.dto.ProductExecution;
import ssm.wjx.entity.Product;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.entity.Shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        ProductCategory pc = new ProductCategory();
        shop.setShopId(18L);
        pc.setProductCategoryId(2L);
        Product product = new Product();
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("product test");
        product.setImgAddr("three");
        product.setNormalPrice("100$");
        product.setPriority(5);
        product.setEnableStatus(1);
        File thumbnail = new File("D:\\FILE\\dog.jpg");
        File productFile1 = new File("D:\\FILE\\test.jpg");
        File productFile2 = new File("D:\\FILE\\test1.jpg");
        ImageHolder imageHolder = new ImageHolder(thumbnail.getName(), new FileInputStream(thumbnail));
        ImageHolder imageHolder1 = new ImageHolder(productFile1.getName(), new FileInputStream(productFile1));
        ImageHolder imageHolder2 = new ImageHolder(productFile2.getName(), new FileInputStream(productFile2));
        List<ImageHolder> holders = new ArrayList<>();
        holders.add(imageHolder1);
        holders.add(imageHolder2);
        ProductExecution productExecution = productService.addProduct(product, imageHolder, holders);
        Assert.assertEquals(1, productExecution.getState());
    }
    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        ProductCategory pc = new ProductCategory();
        shop.setShopId(18L);
        pc.setProductCategoryId(2L);
        Product product = new Product();
        product.setProductId(12L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("正式产品one欧恩");
        product.setImgAddr("three");
        product.setNormalPrice("1000");
        product.setPriority(5);
        product.setEnableStatus(1);
        File productFile1 = new File("D:\\FILE\\test.jpg");
//        ImageHolder imageHolder = new ImageHolder(thumbnail.getName(), new FileInputStream(thumbnail));
        ImageHolder imageHolder1 = new ImageHolder(productFile1.getName(), new FileInputStream(productFile1));
//        ImageHolder imageHolder2 = new ImageHolder(productFile2.getName(), new FileInputStream(productFile2));
        List<ImageHolder> holders = new ArrayList<>();
        holders.add(imageHolder1);
//        holders.add(imageHolder2);
        ProductExecution productExecution = productService.modifyProduct(product, null, holders);
        Assert.assertEquals(1, productExecution.getState());
    }

}
