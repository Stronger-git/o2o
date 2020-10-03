package ssm.wjx.enums;

public enum ShopStateEnum {
    OFFLINE(-1, "非法店铺"), CHECK(0, "审核中"),
    SUCCESS(1, "操作成功"),PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),NULL_SHOPID(-1002, "ShopId为空"),
    NULL_SHOP(-1003, "店铺信息为空");
    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * 根据传入的state返回相应的enum值
     */
    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
