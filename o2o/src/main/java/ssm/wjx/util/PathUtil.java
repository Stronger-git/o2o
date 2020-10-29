package ssm.wjx.util;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    /**
     * 根据OS的不同，制作图片根路径 win或linux/mac
     * 可以根据操作系统环境而发生变化
     * 返回图片根路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:\\FILE";
        } else {
            basePath = "/user";
        }
        basePath = basePath.replace("\\", separator);
        return basePath;
    }

    /**
     * 返回项目图片子路径
     * @param shopId
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
