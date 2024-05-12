/*
 Navicat Premium Data Transfer

 Source Server         : 001
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : db_market

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 02/01/2024 18:03:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(0) NOT NULL COMMENT '管理员id',
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '管理员名称',
  `username` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '管理员密码',
  `img` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL COMMENT '管理员图片',
  `status` int(0) NOT NULL COMMENT '管理员状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '更新人',
  `del` int(0) NOT NULL COMMENT '逻辑删除,1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1735937095678996482, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '/photo/admin/guanliyuan.jpg', 1, '2023-12-16 16:17:10', '2023-12-28 14:54:52', 1735937095678996500, 1735937095678996482, 0);
INSERT INTO `admin` VALUES (1736295283771224066, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '/photo/admin/guanliyuan.jpg', 1, '2023-12-17 16:00:29', '2023-12-17 16:00:29', 1735937095678996482, 1735937095678996482, 0);

-- ----------------------------
-- Table structure for basket
-- ----------------------------
DROP TABLE IF EXISTS `basket`;
CREATE TABLE `basket`  (
  `id` bigint(0) NOT NULL,
  `userid` bigint(0) NOT NULL,
  `goodsid` bigint(0) DEFAULT NULL,
  `num` bigint(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of basket
-- ----------------------------
INSERT INTO `basket` VALUES (1740292520065015810, 1736318583914250241, 1736256186830725121, 1);
INSERT INTO `basket` VALUES (1740292561496350722, 1736318583914250241, 1736255865538650113, 1);

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `id` bigint(0) NOT NULL COMMENT '商家id',
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商家名称',
  `username` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商家账号',
  `password` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商家密码',
  `phone` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商家手机号',
  `img` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL COMMENT '商家图片地址',
  `detail` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL COMMENT '商家详情',
  `address` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商家地址',
  `status` int(0) NOT NULL COMMENT '商家状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '更新人',
  `del` int(0) NOT NULL COMMENT '逻辑删除,1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES (1735935550497361921, '华为科技有限公司', 'huawei', 'e10adc3949ba59abbe56e057f20f883e', '15369866533', '/photo/business/huawei.png', '数智世界 一触即达', '广东省/深圳市/龙岗区', 1, '2023-12-16 16:11:02', '2023-12-28 14:46:31', 1735935550497362000, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1735935762360045569, '小米科技有限公司', 'xiaomi', 'e10adc3949ba59abbe56e057f20f883e', '15369877499', '/photo/business/xiaomi.jpg', '感动人心、价格厚道', '北京市/市辖区/海淀区', 1, '2023-12-16 16:11:53', '2023-12-21 11:08:03', 1735935762360045600, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1737637070756634626, '贵州茅台', 'maotai', 'e10adc3949ba59abbe56e057f20f883e', '13523699844', '/photo/business/maotai.png', '中国贵州茅台酒厂（集团）有限责任公司,涉足产业包括白酒、保健酒、葡萄酒、证券、保险、银行、文化旅游、教育、房地产、生态农业及白酒上下游产业等， 企业总资产达3301亿元。 ', '贵州/贵阳市/云岩区', 1, '2023-12-21 08:52:16', '2023-12-21 10:55:03', 1735937095678996500, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1741352111008206850, '瑞星咖啡', 'ruixing', 'e10adc3949ba59abbe56e057f20f883e', '19836966355', '/photo/business/ruixing.webp', '创造世界级咖啡品牌，让瑞幸成为人们日常生活的一部分', '福建省/厦门市/思明区', 1, '2023-12-31 14:54:31', '2023-12-31 14:54:31', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1741354471998705666, 'Lenovo联想', 'lenovo', 'e10adc3949ba59abbe56e057f20f883e', '15236983365', '/photo/business/lenovo.jpg', '联想助力客户，把握明日科技，变革今日世界。', '北京市/市辖区/朝阳区', 1, '2023-12-31 15:03:54', '2023-12-31 15:03:54', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1741360728449056769, '悦享图书', 'tushu', 'e10adc3949ba59abbe56e057f20f883e', '15236999655', '/photo/business/null.jpg', '书中自有黄金屋', '河南省/新乡市/延津县', 1, '2023-12-31 15:28:45', '2023-12-31 15:28:45', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1741362112946798593, '悦享水果生鲜', 'shuiguo', 'e10adc3949ba59abbe56e057f20f883e', '15236988744', '/photo/business/null.jpg', '新鲜水果时蔬', '贵州省/六盘水市/水城区', 1, '2023-12-31 15:34:15', '2024-01-02 16:48:13', 1735937095678996500, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1741363007201771521, '悦享衣橱', 'yichu', 'd41d8cd98f00b204e9800998ecf8427e', '18636458877', '/photo/business/null.jpg', '潮流衣物', '天津市/市辖区/河东区', 1, '2023-12-31 15:37:49', '2023-12-31 15:37:49', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1742098447663996930, '大奎金店', 'zhanghua', 'e10adc3949ba59abbe56e057f20f883e', '18836191531', '/photo/business/null.jpg', '黄金珠宝专卖', '北京市/市辖区/西城区', 1, '2024-01-02 16:20:11', '2024-01-02 16:21:03', 1735937095678996500, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1742111683314470914, '老张熟肉', 'zhanghua02', 'e10adc3949ba59abbe56e057f20f883e', '18836191532', '/photo/business/OIP-C (13).jpg', '百年老字号', '河南省/安阳市/汤阴县', 1, '2024-01-02 17:12:47', '2024-01-02 17:12:47', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1742114164752797697, '猪猪水果', 'zhanghua03', 'e10adc3949ba59abbe56e057f20f883e', '18836191533', '/photo/business/gchahua_11624489.jpg.285.jpg', '新鲜水果', '河北省/秦皇岛市/北戴河区', 1, '2024-01-02 17:22:38', '2024-01-02 17:22:38', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1742116028097515521, '老娄烟酒', 'zhanghua04', 'e10adc3949ba59abbe56e057f20f883e', '13683728771', '/photo/business/true.jpg', '酒水专卖', '山西省/大同市/平城区', 1, '2024-01-02 17:30:03', '2024-01-02 17:30:03', 1735937095678996482, 1735937095678996482, 0);
INSERT INTO `business` VALUES (1742118137295556610, '万家百货', 'zhanghua05', 'e10adc3949ba59abbe56e057f20f883e', '18536191538', '/photo/business/下载 (1).jpg', '应有尽有', '辽宁省/大连市/西岗区', 1, '2024-01-02 17:38:26', '2024-01-02 17:38:26', 1735937095678996482, 1735937095678996482, 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(0) NOT NULL,
  `goodsid` bigint(0) NOT NULL,
  `comment` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL,
  `score` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `del` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1736255156780965889, '质量很好', '1735469239277436930', 4, '2024-01-02 09:21:21', 0);
INSERT INTO `comment` VALUES (1742007489743704065, 1736255156780965889, '很好用\n', '1735469239277436930', 4, '2024-01-02 10:18:45', 0);
INSERT INTO `comment` VALUES (1742018188390174722, 1736255156780965889, '很好用\n', '1735469239277436930', 5, '2024-01-02 11:01:16', 0);
INSERT INTO `comment` VALUES (1742104420428304385, 1742103889697853442, '好\n', '1735469239277436930', 5, '2024-01-02 16:43:55', 0);
INSERT INTO `comment` VALUES (1742119330453409794, 1742113158593794050, '我勒个妈呀,是真好吃\n1', '1735469239277436930', 5, '2024-01-02 17:43:10', 0);
INSERT INTO `comment` VALUES (1742119413731315714, 1742113158593794050, '爱了爱了 复购复购\n', '1735469239277436930', 5, '2024-01-02 17:43:30', 0);
INSERT INTO `comment` VALUES (1742119604509233153, 1742115276633427969, '有点小酸\n', '1735469239277436930', 5, '2024-01-02 17:44:15', 0);
INSERT INTO `comment` VALUES (1742119869278867458, 1737671621352534017, '为发热而生?\n', '1735469239277436930', 5, '2024-01-02 17:45:19', 0);
INSERT INTO `comment` VALUES (1742120204768661505, 1737639615260516354, '给叔叔整了两瓶,明年能叫爸了吧\n', '1735469239277436930', 5, '2024-01-02 17:46:39', 0);
INSERT INTO `comment` VALUES (1742120344556425217, 1742110596675162114, '女朋友爱的死去活来\n', '1735469239277436930', 5, '2024-01-02 17:47:12', 0);
INSERT INTO `comment` VALUES (1742120415083646978, 1742116855608528897, '屌丝爱喝\n', '1735469239277436930', 5, '2024-01-02 17:47:29', 0);
INSERT INTO `comment` VALUES (1742120491629694978, 1741352688274460674, '有点小贵\n', '1735469239277436930', 3, '2024-01-02 17:47:47', 0);
INSERT INTO `comment` VALUES (1742120610156531714, 1742115442815946754, '感觉像是大棚的,不像是进口的\n', '1735469239277436930', 2, '2024-01-02 17:48:15', 0);
INSERT INTO `comment` VALUES (1742120684991303682, 1742110090951151617, '猛屯一波\n', '1735469239277436930', 5, '2024-01-02 17:48:33', 0);
INSERT INTO `comment` VALUES (1742120748753113090, 1742117078242185217, '真乃国货之光\n', '1735469239277436930', 5, '2024-01-02 17:48:48', 0);
INSERT INTO `comment` VALUES (1742120855116468226, 1742118981776723970, '等他啥时候打折9.9我就买\n', '1735469239277436930', 5, '2024-01-02 17:49:14', 0);
INSERT INTO `comment` VALUES (1742121113573675009, 1742118295852830721, '满满的童年回忆\n', '1735936921481162753', 5, '2024-01-02 17:50:15', 0);
INSERT INTO `comment` VALUES (1742121164760961025, 1736255156780965889, '遥遥领先\n', '1735936921481162753', 5, '2024-01-02 17:50:27', 0);
INSERT INTO `comment` VALUES (1742121261104123905, 1742114763028320258, '不错不错\n', '1735936921481162753', 5, '2024-01-02 17:50:50', 0);
INSERT INTO `comment` VALUES (1742121325360861186, 1741355976722055169, '等打折吧\n', '1735936921481162753', 5, '2024-01-02 17:51:06', 0);
INSERT INTO `comment` VALUES (1742121409259524097, 1742110957754404866, '插个眼,坐等打折\n', '1735936921481162753', 5, '2024-01-02 17:51:26', 0);
INSERT INTO `comment` VALUES (1742121506605125633, 1742112508560560130, '大口吃肉大口喝酒\n', '1735936921481162753', 5, '2024-01-02 17:51:49', 0);
INSERT INTO `comment` VALUES (1742121600423317505, 1742114447247560706, '挺甜的,再给老妈买点\n', '1735936921481162753', 5, '2024-01-02 17:52:11', 0);
INSERT INTO `comment` VALUES (1742121704123289602, 1742118809910923265, '爱吃\n', '1735936921481162753', 5, '2024-01-02 17:52:36', 0);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(0) NOT NULL COMMENT '商品id',
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商品名',
  `type` bigint(0) DEFAULT NULL COMMENT '类别',
  `img` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL COMMENT '图片地址',
  `detail` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL COMMENT '商品详情',
  `businessid` bigint(0) NOT NULL COMMENT '所属商品id',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `stock` int(0) NOT NULL COMMENT '库存',
  `status` int(0) NOT NULL COMMENT '商品状态',
  `del` int(0) NOT NULL COMMENT '逻辑删除,1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1736255156780965889, '华为mate60', 1735967691075661826, '/photo/goods/huaweimate60.png', '遥遥领先', 1735935550497361921, 5499.00, 2199, 1, 0);
INSERT INTO `goods` VALUES (1736255865538650113, '华为nova9SE', 1735967691075661826, '/photo/goods/华为nova9SE.webp', '顺丰速发/送充电器', 1735935550497361921, 3999.00, 1156, 1, 0);
INSERT INTO `goods` VALUES (1736256186830725121, '华为P40', 1735967691075661826, '/photo/goods/华为P40.png', '顺丰速发/麒麟990/鸿蒙系统', 1735935550497361921, 2298.00, 999, 1, 0);
INSERT INTO `goods` VALUES (1736953559133405185, '遥遥领先', 1735967691075661826, '/photo/goods/null.jpg', '遥遥领先于同行', 1735935550497361921, 9999.00, 1, 0, 0);
INSERT INTO `goods` VALUES (1737397752112836610, '华为mate60pro', 1735967691075661826, '/photo/goods/huaweimate60pro.webp', 'mate60pro/顺丰包邮', 1735935550497361921, 9999.00, 1000, 1, 0);
INSERT INTO `goods` VALUES (1737639615260516354, '茅台', 1737638881248927746, '/photo/goods/maotai.jpg', '禁止未成年人饮酒', 1737637070756634626, 3999.00, 600, 1, 0);
INSERT INTO `goods` VALUES (1737671621352534017, '小米11pro', 1735967691075661826, '/photo/goods/xiaomi11pro.jpg', 'xiaomi11pro', 1735935762360045569, 5999.00, 200, 1, 0);
INSERT INTO `goods` VALUES (1741352688274460674, '大师咖啡', 1737638881248927746, '/photo/goods/dashi.jpg', '大师手法', 1741352111008206850, 19.90, 500, 1, 0);
INSERT INTO `goods` VALUES (1741355976722055169, '联想拯救者Y9000P', 1735967516613586945, '/photo/goods/联想拯救者Y9000P.png', ' 2023 英特尔酷睿i9 16英寸电竞笔记本电脑', 1741354471998705666, 9499.00, 1000, 1, 0);
INSERT INTO `goods` VALUES (1742100249851482113, '黄金', 1742096066423406594, '/photo/goods/null.jpg', '保真', 1742098447663996930, 25.00, 2, 1, 1);
INSERT INTO `goods` VALUES (1742103889697853442, '黄金', 1742096066423406594, '/photo/goods/ruixing.webp', '保真', 1742098447663996930, 100.00, 100, 1, 1);
INSERT INTO `goods` VALUES (1742110090951151617, '财富金条(10克)', 1742096066423406594, '/photo/goods/OIP-C.jpg', '财富金条纯金足金', 1742098447663996930, 4875.00, 10, 1, 0);
INSERT INTO `goods` VALUES (1742110596675162114, 'DR求婚钻戒', 1742096066423406594, '/photo/goods/OIP-C (2).jpg', '雪吻钻石', 1742098447663996930, 3359.00, 5, 1, 0);
INSERT INTO `goods` VALUES (1742110957754404866, '玲珑项链', 1742096066423406594, '/photo/goods/OIP-C (1).jpg', '个性高级', 1742098447663996930, 1000.00, 5, 1, 0);
INSERT INTO `goods` VALUES (1742112094456926210, '老庙牛肉', 1740534378938703873, '/photo/goods/cfe889631ad825d9.jpg', '河南特产,开袋即食.', 1742111683314470914, 50.49, 100, 1, 0);
INSERT INTO `goods` VALUES (1742112508560560130, '道口烧鸡', 1740534378938703873, '/photo/goods/OIP-C (14).jpg', '安阳滑县特产', 1742111683314470914, 25.00, 58, 1, 0);
INSERT INTO `goods` VALUES (1742112913134735362, '北京烤鸭', 1740534378938703873, '/photo/goods/014b3f59e5b71da80120446389980b.jpg@2o.jpg', '烤鸭商用批整只发加热手撕零食真空芋泥香', 1742111683314470914, 48.00, 200, 1, 0);
INSERT INTO `goods` VALUES (1742113158593794050, '饕餮牛肉干', 1740534378938703873, '/photo/goods/OIP-C (7).jpg', '麻辣鲜香', 1742111683314470914, 58.00, 220, 1, 0);
INSERT INTO `goods` VALUES (1742114447247560706, '苹果', 1740534483959881729, '/photo/goods/R-C.jpg', '红富士', 1742114164752797697, 5.00, 50, 1, 0);
INSERT INTO `goods` VALUES (1742114635991240706, '梨', 1740534483959881729, '/photo/goods/OIP-C (3).jpg', '汁水饱满', 1742114164752797697, 4.00, 40, 1, 0);
INSERT INTO `goods` VALUES (1742114763028320258, '香蕉', 1740534483959881729, '/photo/goods/OIP-C (4).jpg', '新鲜采摘', 1742114164752797697, 3.00, 30, 1, 0);
INSERT INTO `goods` VALUES (1742115276633427969, '草莓', 1740534483959881729, '/photo/goods/OIP-C (5).jpg', '奶油草莓', 1742114164752797697, 38.00, 123, 1, 0);
INSERT INTO `goods` VALUES (1742115442815946754, '车厘子', 1740534483959881729, '/photo/goods/OIP-C (6).jpg', '智利进口', 1742114164752797697, 58.00, 100, 1, 0);
INSERT INTO `goods` VALUES (1742116215121530882, '五粮液', 1737638881248927746, '/photo/goods/OIP-C (12).jpg', '粮食酒', 1742116028097515521, 588.00, 10, 1, 0);
INSERT INTO `goods` VALUES (1742116393664663553, '汾酒', 1737638881248927746, '/photo/goods/OIP-C (11).jpg', '30年汾酒', 1742116028097515521, 1264.00, 13, 1, 0);
INSERT INTO `goods` VALUES (1742116748389535746, '老白干', 1737638881248927746, '/photo/goods/OIP-C (10).jpg', '高度白酒', 1742116028097515521, 45.00, 30, 1, 0);
INSERT INTO `goods` VALUES (1742116855608528897, '可乐', 1737638881248927746, '/photo/goods/R-C (3).jpg', NULL, 1742116028097515521, 3.00, 50, 1, 0);
INSERT INTO `goods` VALUES (1742116925615656962, '雪碧', 1737638881248927746, '/photo/goods/OIP-C (8).jpg', NULL, 1742116028097515521, 3.00, 50, 1, 0);
INSERT INTO `goods` VALUES (1742117078242185217, '汇源果汁', 1737638881248927746, '/photo/goods/OIP-C (9).jpg', '100%果汁', 1742116028097515521, 15.00, 25, 1, 0);
INSERT INTO `goods` VALUES (1742118295852830721, '雪米饼', 1740534378938703873, '/photo/goods/2657707464_1720607082.jpg', NULL, 1742118137295556610, 8.00, 20, 1, 0);
INSERT INTO `goods` VALUES (1742118712561127426, '爆米花', 1740534378938703873, '/photo/goods/asd.jpg', NULL, 1742118137295556610, 6.00, 10, 1, 0);
INSERT INTO `goods` VALUES (1742118809910923265, '可比克', 1740534378938703873, '/photo/goods/R-C (1).jpg', NULL, 1742118137295556610, 5.00, 20, 1, 0);
INSERT INTO `goods` VALUES (1742118884779249665, '虾条', 1740534378938703873, '/photo/goods/R-C (2).jpg', NULL, 1742118137295556610, 5.00, 20, 1, 0);
INSERT INTO `goods` VALUES (1742118981776723970, '卫生纸', 1742096066423406594, '/photo/goods/下载.jpg', NULL, 1742118137295556610, 15.00, 10, 1, 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(0) NOT NULL,
  `sender` bigint(0) NOT NULL,
  `receive` bigint(0) NOT NULL,
  `message` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci DEFAULT NULL,
  `isnew` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `create_user` bigint(0) NOT NULL,
  `update_user` bigint(0) NOT NULL,
  `del` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1735469239277436930, 1735936921481162753, '你好呀', 0, '2023-12-27 17:54:08', '2023-12-27 17:54:13', 1735936921481162753, 1735936921481162753, '0');
INSERT INTO `message` VALUES (1739119114271965185, 1735936921481162753, 1735469239277436930, '你好', 0, '2023-12-25 11:01:23', '2024-01-02 16:37:07', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1740192309384708098, 1735469239277436930, 1736318583914250241, '你好\n', 0, '2023-12-28 10:05:52', '2023-12-28 10:05:52', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1740192347775172610, 1735469239277436930, 1735935550497361921, '你好\n', 0, '2023-12-28 10:06:02', '2023-12-30 18:12:02', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1740207265215807490, 1735935550497361921, 1735469239277436930, '您好\n', 0, '2023-12-28 11:05:18', '2024-01-02 16:45:26', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1740293624341372929, 1736318583914250241, 1735935550497361921, '你好\n', 0, '2023-12-28 16:48:28', '2023-12-30 18:11:58', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1741028863116591105, 1735935550497361921, 1736318583914250241, '您好', 1, '2023-12-30 17:30:02', '2023-12-30 17:30:02', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1741032688279896066, 1735469239277436930, 1735935550497361921, '我购买了<华为mate60>请尽快发货', 0, '2023-12-30 17:45:14', '2023-12-30 18:12:02', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1741032814356480002, 1735935550497361921, 1735469239277436930, '已经发货,请注意查收', 0, '2023-12-30 17:45:44', '2024-01-02 16:45:26', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1742100445121499138, 1742098447663996930, 1735469239277436930, '对于我们的黄金\n', 0, '2024-01-02 16:28:07', '2024-01-02 16:47:19', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1742103731685838850, 1742098447663996930, 1735469239277436930, '你好\n', 0, '2024-01-02 16:41:11', '2024-01-02 16:47:19', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1742104718391660546, 1735469239277436930, 1742098447663996930, '我购买了<黄金>请尽快发货', 0, '2024-01-02 16:45:06', '2024-01-02 16:46:43', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1742104718391660547, 1735469239277436930, 1735935550497361921, '我购买了<华为nova9SE>请尽快发货', 1, '2024-01-02 16:45:06', '2024-01-02 16:45:06', 1735937095678996482, 1735937095678996482, '0');
INSERT INTO `message` VALUES (1742105082318835713, 1742098447663996930, 1735469239277436930, '已经发货,请注意查收', 0, '2024-01-02 16:46:33', '2024-01-02 16:47:19', 1735937095678996482, 1735937095678996482, '0');

-- ----------------------------
-- Table structure for order1
-- ----------------------------
DROP TABLE IF EXISTS `order1`;
CREATE TABLE `order1`  (
  `id` bigint(0) NOT NULL COMMENT '订单id',
  `userid` bigint(0) NOT NULL COMMENT '用户id',
  `pay` int(0) NOT NULL COMMENT '是否付款,1为付款,0为未付款',
  `post` int(0) NOT NULL COMMENT '是否发货,1为已发货,0为未发货',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更改时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人',
  `update_user` bigint(0) NOT NULL COMMENT '更改人',
  `del` int(0) NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order1
-- ----------------------------
INSERT INTO `order1` VALUES (1741032688145678338, 1735469239277436930, 1, 1, '2023-12-30 17:45:14', '2023-12-30 17:45:44', 1735937095678996500, 1735937095678996482, 0);
INSERT INTO `order1` VALUES (1742104718454575106, 1735469239277436930, 1, 1, '2024-01-02 16:45:06', '2024-01-02 16:46:33', 1735937095678996500, 1735937095678996482, 0);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` bigint(0) NOT NULL COMMENT '商品类别id',
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '商品类别名称',
  `img` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '图片',
  `del` int(0) NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1735967516613586945, '笔记本', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1735967659094093825, '家用电器', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1735967691075661826, '手机', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1737638881248927746, '酒水饮料', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740534378938703873, '网红美食', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740534433980555266, '潮牌衣物', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740534483959881729, '水果生鲜', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740534542709497858, '数码潮玩', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740535067685363713, '进口好物', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1740535565104652290, '图书教育', '/photo/type/null.jpg', 0);
INSERT INTO `type` VALUES (1742096066423406594, '居家优品', '/photo/type/null.jpg', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '密码',
  `img` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '头像',
  `sex` varchar(2) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '性别',
  `phone` varchar(64) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '手机号',
  `id_number` varchar(18) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '身份证号',
  `address` varchar(255) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL COMMENT '地址',
  `status` int(0) NOT NULL COMMENT '状态:0为禁用,1为正常',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(0) NOT NULL COMMENT '创建人id',
  `update_user` bigint(0) NOT NULL COMMENT '更改人id',
  `del` int(0) NOT NULL COMMENT '删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1735469239277436930, '李四', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '/photo/user/user3.png', '女', '13836955422', '12345678987654321', '河南省/新乡市/红旗区', 1, '2023-12-15 09:18:05', '2024-01-02 16:48:47', 1735469239277437000, 1735937095678996482, 0);
INSERT INTO `user` VALUES (1735936921481162753, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '/photo/user/user2.png', '男', '15369896699', '12345678987654321', '天津市/市辖区/河东区', 1, '2023-12-16 16:16:29', '2024-01-02 17:53:23', 1735936921481162800, 1735937095678996482, 0);
INSERT INTO `user` VALUES (1736318583914250241, '王五', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '/photo/user/user1.png', '男', '13836955499', '12345678987654321', '河南省/新乡市', 0, '2023-12-17 17:33:04', '2023-12-17 17:33:04', 1735937095678996482, 1735937095678996482, 0);

-- ----------------------------
-- Table structure for user_goods
-- ----------------------------
DROP TABLE IF EXISTS `user_goods`;
CREATE TABLE `user_goods`  (
  `id` bigint(0) NOT NULL COMMENT '用来保证唯一的主键',
  `orderid` bigint(0) NOT NULL COMMENT '用户id',
  `goodsid` bigint(0) NOT NULL COMMENT '商品id',
  `num` int(0) NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf16 COLLATE = utf16_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_goods
-- ----------------------------
INSERT INTO `user_goods` VALUES (1741032688145678339, 1741032688145678338, 1736255156780965889, 2);
INSERT INTO `user_goods` VALUES (1742104718517489666, 1742104718454575106, 1736255865538650113, 1);

SET FOREIGN_KEY_CHECKS = 1;
