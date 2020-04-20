package ssm.wjx.dao;

import org.apache.ibatis.annotations.Param;
import ssm.wjx.entity.Product;

import java.util.List;

public interface ProductDao {
    int insertProduct(Product product);
    int updateProduct(Product product);
    Product selectById(Long productId);
    int deleteById(Long productId);
    // 查询列表并分页， 可输入的条件有：商品名（模糊），商品状态，店铺ID，商品类别
    List<Product> selectAllByProduct(@Param("product")Product product, @Param("offset")Integer offset, @Param("limit")Integer limit);
    Long selectCountByProduct(Product product);
    // 解除与商品类别之间的关联
    int updateProductCategoryToNull(Long id);
}
