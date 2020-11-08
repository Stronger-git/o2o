/*
 Navicat Premium Data Transfer

 Source Server         : AliCloud
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 123.57.77.132:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/11/2020 18:33:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`  (
  `area_id` int(2) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`area_id`) USING BTREE,
  UNIQUE INDEX `UK_AREA`(`area_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES (1, '四合院', 9, NULL, NULL);
INSERT INTO `tb_area` VALUES (2, '小西门', 8, NULL, NULL);
INSERT INTO `tb_area` VALUES (3, '南门', 7, NULL, NULL);

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line`  (
  `line_id` int(100) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `line_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
INSERT INTO `tb_head_line` VALUES (1, NULL, '#', '\\upload\\images\\item\\headtitle\\202002151051.jpeg', 1, 1, NULL, NULL);
INSERT INTO `tb_head_line` VALUES (2, NULL, '#', '\\upload\\images\\item\\headtitle\\202002151052.jpg', 2, 1, NULL, NULL);
INSERT INTO `tb_head_line` VALUES (3, NULL, '#', '\\upload\\images\\item\\headtitle\\202002151053.jpg', 3, 1, NULL, NULL);

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth`  (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`) USING BTREE,
  UNIQUE INDEX `uk_local_profile`(`username`) USING BTREE,
  INDEX `fk_localauth_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------
INSERT INTO `tb_local_auth` VALUES (2, 5, 'monkey', 'd0763edaa9d9bd2a9516280e9044d885', '2020-10-03 18:17:06', '2020-10-03 18:17:06');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `normal_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `promotion_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `fk_product_procate`(`product_category_id`) USING BTREE,
  INDEX `fk_product_shop`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES (2, '可口可乐', '冰镇常温都有', '\\upload\\item\\shop\\1\\2020021410341257011.png', '5', '2', 3, '2020-02-14 10:34:13', '2020-02-14 10:34:13', 1, 15, 1);
INSERT INTO `tb_product` VALUES (3, '雪碧', '透心凉，心飞扬！', '\\upload\\item\\shop\\1\\2020021410422775634.jpg', '5', '2', 1, '2020-02-14 10:42:28', '2020-02-14 10:42:28', 1, 15, 1);
INSERT INTO `tb_product` VALUES (4, '小米锅巴', '锅巴', '\\upload\\item\\shop\\2\\2020021411415543331.jpg', '15', '10', 5, '2020-02-14 11:41:56', '2020-02-14 11:42:10', 0, 10, 2);
INSERT INTO `tb_product` VALUES (5, 'bug蛋糕', '123', '\\upload\\item\\shop\\2\\2020021411524454637.jpg', '5', '5', 5, '2020-02-14 11:52:44', '2020-02-14 11:52:44', 1, 9, 2);
INSERT INTO `tb_product` VALUES (6, '芒果班戟', 'qqq', '\\upload\\item\\shop\\1\\2020050611403099436.jpg', '15', '99', 9, '2020-02-14 13:55:43', '2020-05-06 03:57:40', 1, 13, 1);
INSERT INTO `tb_product` VALUES (7, '拿铁咖啡', '意大利人早晨的厨房里，照得到阳光的炉子上通常会同时煮着咖啡和牛奶。喝拿铁的意大利人，与其说他们喜欢意大利浓缩咖啡，不如说他们喜欢牛奶，也只有咖啡才能给普普通通的牛奶带来让人难以忘怀的味道', '\\upload\\item\\shop\\1\\2020021915562240494.jpg', '78', '89', 9, '2020-02-19 15:56:22', '2020-09-05 14:04:38', 1, 43, 1);
INSERT INTO `tb_product` VALUES (8, '测试咖啡', '测试咖啡1', '\\upload\\item\\shop\\2\\2020090509252312243.jpg', '23', '13', 35, '2020-09-05 09:25:23', '2020-09-05 09:25:23', 1, 42, 2);

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category`  (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int(2) DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_category_id`) USING BTREE,
  INDEX `fk_product_category`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_product_category` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES (9, '抹茶蛋糕', 1, NULL, 2);
INSERT INTO `tb_product_category` VALUES (10, '锅巴', 3, NULL, 2);
INSERT INTO `tb_product_category` VALUES (13, '甜品', 0, NULL, 1);
INSERT INTO `tb_product_category` VALUES (14, '奶茶', 1, NULL, 1);
INSERT INTO `tb_product_category` VALUES (15, '饮料', 2, NULL, 1);
INSERT INTO `tb_product_category` VALUES (42, '咖啡', 7, NULL, 2);
INSERT INTO `tb_product_category` VALUES (43, '咖啡', 3, NULL, 1);
INSERT INTO `tb_product_category` VALUES (44, '奶茶', 2, NULL, 2);

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img`  (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `img_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `priority` int(2) DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`) USING BTREE,
  INDEX `fk_proimg_product`(`product_id`) USING BTREE,
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES (1, '\\upload\\item\\shop\\1\\2020021410341394014.jpg', NULL, 1, '2020-02-14 10:34:13', 2);
INSERT INTO `tb_product_img` VALUES (2, '\\upload\\item\\shop\\1\\2020021410341384012.jpg', NULL, 1, '2020-02-14 10:34:13', 2);
INSERT INTO `tb_product_img` VALUES (3, '\\upload\\item\\shop\\1\\2020021410422778100.jpg', NULL, 1, '2020-02-14 10:42:28', 3);
INSERT INTO `tb_product_img` VALUES (4, '\\upload\\item\\shop\\1\\2020021410422726336.jpg', NULL, 1, '2020-02-14 10:42:29', 3);
INSERT INTO `tb_product_img` VALUES (5, '\\upload\\item\\shop\\2\\2020021411415610893.jpg', NULL, 1, '2020-02-14 11:41:56', 4);
INSERT INTO `tb_product_img` VALUES (6, '\\upload\\item\\shop\\2\\2020021411415686272.jpg', NULL, 1, '2020-02-14 11:41:56', 4);
INSERT INTO `tb_product_img` VALUES (7, '\\upload\\item\\shop\\2\\2020021411524474125.jpg', NULL, 1, '2020-02-14 11:52:44', 5);
INSERT INTO `tb_product_img` VALUES (9, '\\upload\\item\\shop\\1\\2020021915562264578.jpg', NULL, 1, '2020-02-19 15:56:22', 7);
INSERT INTO `tb_product_img` VALUES (10, '\\upload\\item\\shop\\1\\2020021915562265684.jpg', NULL, 1, '2020-02-19 15:56:22', 7);
INSERT INTO `tb_product_img` VALUES (11, '\\upload\\item\\shop\\1\\2020021915562286460.jpeg', NULL, 1, '2020-02-19 15:56:22', 7);
INSERT INTO `tb_product_img` VALUES (13, '\\upload\\item\\shop\\1\\2020050611403036127.jpeg', NULL, 1, '2020-05-06 03:40:31', 6);
INSERT INTO `tb_product_img` VALUES (14, '\\upload\\item\\shop\\1\\2020050611403068628.jpg', NULL, 1, '2020-05-06 03:40:31', 6);
INSERT INTO `tb_product_img` VALUES (15, '\\upload\\item\\shop\\1\\2020050611403072165.jpg', NULL, 1, '2020-05-06 03:40:31', 6);
INSERT INTO `tb_product_img` VALUES (16, '\\upload\\item\\shop\\2\\2020090509252383557.jpg', NULL, 1, '2020-09-05 09:25:24', 8);
INSERT INTO `tb_product_img` VALUES (17, '\\upload\\item\\shop\\2\\2020090509252398192.jpg', NULL, 1, '2020-09-05 09:25:24', 8);

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL,
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `shop_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `shop_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `priority` int(3) DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`shop_id`) USING BTREE,
  INDEX `fk_shop_area`(`area_id`) USING BTREE,
  INDEX `fk_shop_profile`(`owner_id`) USING BTREE,
  INDEX `fk_shop_shopcategory`(`shop_category_id`) USING BTREE,
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_shopcategory` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES (1, 5, 1, 9, '瑞幸咖啡', '瑞幸咖啡以“从咖啡开始，让瑞幸成为人们日常生活的一部分”为愿景 [5]  ，通过充分利用移动互联网和大数据技术的新零售模式，与各领域顶级供应商深度合作，致力为客户提供高品质、高性价比、高便利性的产品', '北京朝阳路白家湾', '430-127381221', '\\upload\\item\\shop\\1\\2020021313263274690.jpg', 5, '2020-02-13 13:26:32', '2020-02-13 13:26:32', 1, NULL);
INSERT INTO `tb_shop` VALUES (2, 5, 2, 9, '星巴克', '其总部坐落美国华盛顿州西雅图市。星巴克旗下零售产品包括30多款全球顶级的咖啡豆、手工制作的浓缩咖啡和多款咖啡冷热饮料、新鲜美味的各式糕点食', '聊城利民路极客湾附近', '400-820-6998', '\\upload\\item\\shop\\2\\2020021313291076878.png', 3, '2020-02-13 13:29:11', '2020-02-13 13:29:11', 1, NULL);
INSERT INTO `tb_shop` VALUES (3, 5, 3, 9, '一点点', '一点点奶茶加盟连锁品牌是源自台湾的原50岚奶茶加盟品牌,1994年创办,2010年开放50岚加盟,因在大陆商标已被注册,更名1点点奶茶,一点点奶茶加盟电话', '市中区万达附近', '31231232222', '\\upload\\item\\shop\\3\\2020021313315631565.jpg', 1, '2020-02-13 13:31:57', '2020-02-13 13:31:57', 0, NULL);
INSERT INTO `tb_shop` VALUES (4, 5, 1, 7, '蓝白', '海鲜见面焖面的江湖一半在海鲜见面...\n\n海鲜见面网站', '四合院三楼', '17861901655', '\\upload\\item\\shop\\4\\2020021313335369516.jpg', 2, '2020-02-13 13:33:54', '2020-02-13 13:33:54', 0, NULL);
INSERT INTO `tb_shop` VALUES (5, 5, 1, 8, '海底捞', '一条龙服务', '振华超市三楼', '400-820-6998', NULL, 0, NULL, NULL, 0, NULL);
INSERT INTO `tb_shop` VALUES (6, 5, 2, 10, '印萌打印', '远程打印，打印 配送', '四合院一楼', '17861901698', NULL, 1, NULL, NULL, 0, NULL);
INSERT INTO `tb_shop` VALUES (7, 2, 1, 11, '广州娱乐', '休闲娱乐测试', '广州', '3389829', '\\upload\\item\\shop\\7\\2020022209061056259.jpg', 3, '2020-02-22 09:06:11', '2020-02-22 09:06:11', 0, NULL);
INSERT INTO `tb_shop` VALUES (8, 2, 3, 12, '大悦城', '假日狂欢', '杭州', '400-820-6998', '\\upload\\item\\shop\\7\\2020022209061056259.jpg', 2, NULL, NULL, 1, NULL);

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category`  (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `shop_category_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `priority` int(2) DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`) USING BTREE,
  INDEX `fk_shop_category_self`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
INSERT INTO `tb_shop_category` VALUES (1, '美食饮品', '自助餐大排档', '\\upload\\item\\shopcategory\\milktea.png', 3, NULL, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (2, '休闲玩乐', '按摩足疗KTV酒吧', '\\upload\\item\\shopcategory\\play.png', 2, NULL, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (3, '电影/演出', '万达全城', '\\upload\\item\\shopcategory\\film.png', 1, NULL, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (4, '广告印刷', '快照印刷', '\\upload\\item\\shopcategory\\print.png', 0, NULL, NULL, NULL);
INSERT INTO `tb_shop_category` VALUES (6, '火锅', '火锅+1', NULL, 11, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (7, '小吃快餐', '小吃快餐+1', NULL, 12, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (8, '自助餐大排档', '自助餐自助餐大排档+1', NULL, 13, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (9, '咖啡奶茶', '咖啡奶茶+1', NULL, 3, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (10, '打印店', '证件照文件印刷', NULL, 2, NULL, NULL, 4);
INSERT INTO `tb_shop_category` VALUES (11, '抖音快手', '抖音快手网红培养', NULL, 3, NULL, NULL, 2);
INSERT INTO `tb_shop_category` VALUES (12, '电子竞技', '电子竞技+1', NULL, 1, NULL, NULL, 2);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `profile_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `user_type` int(2) NOT NULL DEFAULT 1,
  `create_time` datetime(0) DEFAULT NULL,
  `last_edit_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (2, '勇者', 'url:img', NULL, '男', 1, 1, '2020-02-26 20:27:46', '2020-02-26 20:27:46');
INSERT INTO `tb_user` VALUES (3, '勇者', 'url:img', NULL, '男', 1, 1, '2020-02-26 21:54:25', '2020-02-26 21:54:25');
INSERT INTO `tb_user` VALUES (5, 'Wonkey', 'https://thirdwx.qlogo.cn/mmopen/vi_32/JIPSibMdO0JIwHvdia5md86ELP5WSQH5uf4n99GwKYNmuP22GDlhajBca83FYHLQmU4Ng2Y9x2BxRBksAnC32jOg/132', NULL, '男', 1, 2, NULL, NULL);

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth`  (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`) USING BTREE,
  UNIQUE INDEX `open_id_UNIQUE`(`open_id`) USING BTREE,
  UNIQUE INDEX `open_id`(`open_id`) USING BTREE,
  INDEX `fk_wechatauth_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_wechat_auth
-- ----------------------------
INSERT INTO `tb_wechat_auth` VALUES (3, 5, 'oNguIxGes6xBUkj8bQa2fYPAJ7s0', '2020-10-03 18:13:26');

SET FOREIGN_KEY_CHECKS = 1;
