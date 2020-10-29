package ssm.wjx.service;

import ssm.wjx.dto.ProductCategoryExecution;
import ssm.wjx.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(Long shopId);
    ProductCategoryExecution addProductCategories(List<ProductCategory> productCategories);
    ProductCategoryExecution removeProductCategory(Long productCategoryId, Long shopId);
}
