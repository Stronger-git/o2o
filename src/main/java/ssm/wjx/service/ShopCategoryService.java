package ssm.wjx.service;

import ssm.wjx.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    String SHOPCATEGORY_KEY = "shop_category";
    int expire = 18000;
    List<ShopCategory> getShopCategoryList(ShopCategory category);
}
