package ssm.wjx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssm.wjx.dao.ProductCategoryDao;
import ssm.wjx.dao.ProductDao;
import ssm.wjx.dto.ProductCategoryExecution;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.enums.ProductCategoryEnum;
import ssm.wjx.service.ProductCategoryService;

import java.util.Date;
import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.selectByShopId(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution addProductCategories(List<ProductCategory> productCategories) {
        if (productCategories != null && productCategories.size() > 0) {
            try {
                int i = productCategoryDao.insertBatch(productCategories);
                if (i < productCategories.size()) {
                    return new ProductCategoryExecution(ProductCategoryEnum.INNER_ERROR);
                } else {
                    return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS, productCategories);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryEnum.NULL_PRODUCTCATEORIES);
        }
    }

    /**
     * 删除商品类别
     * 因为某个店铺下的商品属于商品类品 一旦删除类别，则应将商品与商品类别失去关联
     * 这里我们只需要要更新product表 将其中的product_category_id置为null 就可以与
     * product_category表解除关联 而不是去删除该类别下的所有商品
     * 当删除商品类别时，若该商品类别下有商品则应该将商品置空
     * 多个DAO操作 应该添加事务
     * @param productCategoryId
     * @param shopId
     * @return
     */
    @Override
    @Transactional
    public ProductCategoryExecution removeProductCategory(Long productCategoryId, Long shopId) {
        // 将此商品类别下的商品的类别ID置为空
        try {
            productDao.updateProductCategoryToNull(productCategoryId);
            int i = productCategoryDao.deleteById(productCategoryId, shopId);
            if (i < 1) {
                throw new RuntimeException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new RuntimeException("商品类别删除失败");
        }
    }

}
