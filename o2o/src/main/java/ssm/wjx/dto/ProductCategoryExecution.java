package ssm.wjx.dto;

import ssm.wjx.entity.ProductCategory;
import ssm.wjx.enums.ProductCategoryEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    List<ProductCategory> productCategories;
    public ProductCategoryExecution(){}
    public ProductCategoryExecution(ProductCategoryEnum productCategoryEnum) {
        this.state = productCategoryEnum.getState();
        this.stateInfo = productCategoryEnum.getStateInfo();
    }
    public ProductCategoryExecution(ProductCategoryEnum productCategoryEnum, List<ProductCategory> productCategories) {
        this.state = productCategoryEnum.getState();
        this.stateInfo = productCategoryEnum.getStateInfo();
        this.productCategories = productCategories;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
