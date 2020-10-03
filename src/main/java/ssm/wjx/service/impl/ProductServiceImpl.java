package ssm.wjx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssm.wjx.dao.ProductDao;
import ssm.wjx.dao.ProductImgDao;
import ssm.wjx.dto.ImageHolder;
import ssm.wjx.dto.ProductExecution;
import ssm.wjx.entity.Product;
import ssm.wjx.entity.ProductCategory;
import ssm.wjx.entity.ProductImg;
import ssm.wjx.entity.Shop;
import ssm.wjx.enums.ProductEnum;
import ssm.wjx.service.ProductService;
import ssm.wjx.util.ImageUtil;
import ssm.wjx.util.PathUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Override
    public ProductExecution getAllProductsByProduct(Product product, int pageIndex, int pageSize) {
        int offset = pageIndexToOffset(pageIndex, pageSize);
        List<Product> products = productDao.selectAllByProduct(product, offset, pageSize);
        // 同样的查询条件返回总数
        int count = Math.toIntExact(productDao.selectCountByProduct(product));
        ProductExecution productExecution = new ProductExecution(ProductEnum.SUCCESS, products);
        productExecution.setCount(count);
        return productExecution;
    }

    public ProductExecution getProduct(Long productId) {
        Product product = productDao.selectById(productId);
        if (product != null)
            return new ProductExecution(ProductEnum.SUCCESS, product);
        else
            return new ProductExecution(ProductEnum.FAIL);
    }
    /*
     * 添加商品业务逻辑
     *  dao insert product use key
     *  store img path
     *  dao insert productImage by key
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageList) {
        Shop shop = product.getShop();
        ProductCategory productCategory = product.getProductCategory();
        if (shop.getShopId() != null && productCategory.getProductCategoryId() != null) {
            // 设置product前端无法设置的默认值 默认上架状态
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            // 1. 处理商品缩略图 插入商品信息记录
            storeThumbnail(thumbnail, product);
            int effectedNum = productDao.insertProduct(product);
            if (effectedNum > 0 && product.getProductId() != null) {
                // 2. 处理商品图片集 插入图片集信息记录
                try {
                    if (imageList != null && imageList.size() > 0)
                        storeImageList(product, imageList);
                } catch (Exception e) {
                    return new ProductExecution(ProductEnum.FAIL);
                }
            } else {
                throw new RuntimeException("插入商品失败");
            }
            return new ProductExecution(ProductEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageList) {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                modifyThumbnail(product, thumbnail);
            }
            int result = productDao.updateProduct(product);
            if (result < 1)
                throw new RuntimeException("修改缩略图失败");
            if (imageList != null && imageList.size() > 0) {
                modifyProductImgs(product, imageList);
            }
            return new ProductExecution(ProductEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductEnum.FAIL);
        }
    }

    private void modifyProductImgs(Product product, List<ImageHolder> newImageList) {
        Product result = productDao.selectById(product.getProductId());
        // 饿汉模式 懒加载， productImgDao.deleteById要放在该语句的下面
        List<ProductImg> productImgList = result.getProductImgList();
        productImgDao.deleteById(product.getProductId());
        if (productImgList != null && productImgList.size() >0) {
            for (ProductImg productImg : productImgList) {
                ImageUtil.deleteFile(productImg.getImgAddr());
            }
            storeImageList(product, newImageList);
        }
    }

    private void modifyThumbnail(Product product, ImageHolder thumbnail) {
        Product result = productDao.selectById(product.getProductId());
        ImageUtil.deleteFile(result.getImgAddr());
        storeThumbnail(thumbnail, product);
    }

    private void storeImageList(Product product, List<ImageHolder> imageList) {
        List<ProductImg> imgs = new ArrayList<>();
        for (ImageHolder imageItem : imageList) {
            String target = PathUtil.getShopImagePath(product.getShop().getShopId());
            String rel = ImageUtil.generateProductImage(imageItem, target);
            ProductImg img = new ProductImg();
            img.setImgAddr(rel);
            img.setProductId(product.getProductId());
            img.setPriority(1);
            img.setCreateTime(new Date());
            imgs.add(img);
        }
        if (imgs.size() > 0) {
            int effectedNum = productImgDao.insertBatch(imgs);
            if (effectedNum < 0) {
                throw new RuntimeException("插入商品图片集失败");
            }
        }
    }

    private void storeThumbnail(ImageHolder thumbnail, Product product) {
        String target = PathUtil.getShopImagePath(product.getShop().getShopId());
        String rel = ImageUtil.generateThumbnail(thumbnail, target);
        product.setImgAddr(rel);
    }
    private int pageIndexToOffset(int pageIndex, int pageSize) {
        return pageIndex < 1?0 : (pageIndex - 1)*pageSize;
    }

}
