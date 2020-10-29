package ssm.wjx.web.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.wjx.dto.ProductExecution;
import ssm.wjx.dto.ShopExecution;
import ssm.wjx.entity.Product;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.entity.Shop;
import ssm.wjx.enums.ProductEnum;
import ssm.wjx.enums.ShopStateEnum;
import ssm.wjx.service.ProductCategoryService;
import ssm.wjx.service.ProductService;
import ssm.wjx.service.ShopService;
import ssm.wjx.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    /**
     * 初始化店铺头信息 商品类别信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopDetail(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId != -1) {
            ShopExecution shopExecution = shopService.getShopById(shopId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
            if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("shop", shopExecution.getShop());
                modelMap.put("productCategories", productCategoryList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", shopExecution.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 获取店铺下指定查询条件下的产品列表
     */
    @RequestMapping(value = "/listproductbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listProductByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex != -1 && pageSize != -1) {
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product product = combineQueryCondition(shopId, productCategoryId, productName);
            ProductExecution productExecution = productService.getAllProductsByProduct(product, pageIndex, pageSize);
            if (productExecution.getState() == ProductEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("count", productExecution.getCount());
                modelMap.put("products", productExecution.getProducts());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productExecution.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize");
        }
        return modelMap;
    }

    private Product combineQueryCondition(long shopId, long productCategoryId, String productName) {
        Product product = new Product();
        if (shopId != -1) {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            product.setShop(shop);
        }
        if (productCategoryId != -1) {
            ProductCategory category = new ProductCategory();
            category.setProductCategoryId(productCategoryId);
            product.setProductCategory(category);
        }
        if (productName != null && !productName.equals("")) {
            product.setProductName(productName);
        }
        // 没有上架的商品不会显示
        product.setEnableStatus(1);
        return product;
    }

}
