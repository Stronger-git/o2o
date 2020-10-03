package ssm.wjx.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ssm.wjx.dto.ImageHolder;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static final Random random = new Random();
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static LocalDateTime dt = LocalDateTime.now();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMultiPartFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }
    /**
     * 保存商铺缩略图到指定路径
     * 对用户上传的文件进行重命名（唯一），然后保存在指定路径下
     * 一般都会建立一个ftp server，将其保存到ftp server中，考虑
     * 资源有限，暂且保存到本地
     * @param thumbnail 缩略图包装体
     * @param targetAddr 包含shopId的目录路径
     * @return 目标图片的相对地址
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getFileName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativePath is :" +relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completePath is :" +PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream())
                    .size(200, 200).watermark(Positions.BOTTOM_LEFT,
                    ImageIO.read(new File(basePath + "/watermark.png")), 0.5f).outputQuality(0.8f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return relativeAddr;
    }

    /**
     * 保存产品图片到指定路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateProductImage(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getFileName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativePath is :" +relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completePath is :" +PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream())
                    .size(370, 256).watermark(Positions.BOTTOM_LEFT,
                    ImageIO.read(new File(basePath + "/watermark.png")), 0.5f).outputQuality(0.9f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return relativeAddr;
    }
    /**
     * 创建目标路径所涉及到的目录，若上级目录不存在，则自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    /**
     * 生成随机文件名 格式 年月日时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        int randNums = random.nextInt(89999) + 10000;
        return dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + randNums;
    }

    public static void deleteFile(String imgPath) {
        File imgFile;
        try {
            imgFile = new File(PathUtil.getImgBasePath() + imgPath);
            if (imgFile.isDirectory()) {
                File[] files = imgFile.listFiles();
                for (File file : files) {
                    file.delete();
                }
                imgFile.delete();
            } else {
                imgFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //thumbnails 图片略图
        // String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // Thumbnails.of(new File("D:\\FILE\\dog.jpg"))
        //         .size(200, 200).watermark(Positions.BOTTOM_LEFT,
        //         ImageIO.read(new File(basePath + "/watermark.png")), 0.5f).outputQuality(0.8f)
        //         .toFile("D:\\FILE\\dognew.jpg");
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}
