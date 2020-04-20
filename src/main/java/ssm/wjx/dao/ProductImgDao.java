package ssm.wjx.dao;

import ssm.wjx.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    int insertBatch(List<ProductImg> productImgs);
    int deleteById(Long productId);
    List<ProductImg> selectAllById(Long productId);
}
