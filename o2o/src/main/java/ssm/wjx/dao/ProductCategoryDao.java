package ssm.wjx.dao;

import org.apache.ibatis.annotations.Param;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.entity.Shop;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> selectByShopId(Long shopId);
    int insertBatch(List<ProductCategory> productCategories);
    int deleteById(@Param("proCatId")Long proCatId, @Param("shopId")Long shopId);
}
