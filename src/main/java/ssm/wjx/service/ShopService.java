package ssm.wjx.service;

import ssm.wjx.dto.ImageHolder;
import ssm.wjx.dto.ShopExecution;
import ssm.wjx.entity.Shop;


import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);
    ShopExecution getShopById(Long id);
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail);
    ShopExecution getShopList(Shop shop, Integer pageIndex, Integer pageSize);
}
