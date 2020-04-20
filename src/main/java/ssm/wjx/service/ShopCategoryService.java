package ssm.wjx.service;

import ssm.wjx.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SHOPCATEGORY_KEY = "shop_category";
    List<ShopCategory> getShopCategoryList(ShopCategory category);
}
