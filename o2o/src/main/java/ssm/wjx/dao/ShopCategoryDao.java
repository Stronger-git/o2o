package ssm.wjx.dao;

import org.apache.ibatis.annotations.Param;
import ssm.wjx.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> selectByShopCategory(@Param("category") ShopCategory category);
}
