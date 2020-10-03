package ssm.wjx.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.ProductImg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao imgDao;
    @Test
    public void testInsertBatch() {
        ProductImg img = new ProductImg();
        ProductImg img1 = new ProductImg();
        List<ProductImg> imgs = new ArrayList<>();
        img.setImgAddr("test");
        img.setCreateTime(new Date());
        img.setProductId(2L);
        img.setPriority(1);
        img1.setImgAddr("test1");
        img1.setCreateTime(new Date());
        img1.setProductId(2L);
        img1.setPriority(2);
        imgs.add(img);
        imgs.add(img1);
        int i = imgDao.insertBatch(imgs);
        Assert.assertEquals(2, i);
    }
    @Test
    public void testSelectAll() {
        List<ProductImg> productImgs = imgDao.selectAllById(2L);
        Assert.assertEquals(2, productImgs.size());
    }
}
