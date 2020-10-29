package ssm.wjx.dao;

import ssm.wjx.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    int insertBatch(List<ProductImg> productImgs);

    /**
     * 删除指定商品下的所有详情图片
     * @param productId
     * @return
     */
    int deleteById(Long productId);
    List<ProductImg> selectAllById(Long productId);
}
