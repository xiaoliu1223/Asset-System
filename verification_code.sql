/*
 Navicat Premium Dump SQL

 Source Server         : zcglxt
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : am

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 24/09/2024 23:08:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of verification_code
-- ----------------------------
INSERT INTO `verification_code` VALUES (16, '{\"code\":\"8390\"}');
INSERT INTO `verification_code` VALUES (17, '{\"code\":\"8800\"}');
INSERT INTO `verification_code` VALUES (18, '{\"code\":\"5876\"}');
INSERT INTO `verification_code` VALUES (19, '{\"code\":\"5958\"}');
INSERT INTO `verification_code` VALUES (20, '{\"code\":\"5004\"}');
INSERT INTO `verification_code` VALUES (21, '{\"code\":\"8302\"}');
INSERT INTO `verification_code` VALUES (22, '{\"code\":\"5666\"}');
INSERT INTO `verification_code` VALUES (23, '{\"code\":\"3762\"}');
INSERT INTO `verification_code` VALUES (24, '{\"code\":\"3695\"}');
INSERT INTO `verification_code` VALUES (25, '{\"code\":\"6614\"}');
INSERT INTO `verification_code` VALUES (26, '{\"code\":\"4517\"}');
INSERT INTO `verification_code` VALUES (27, '{\"code\":\"2148\"}');
INSERT INTO `verification_code` VALUES (28, '{\"code\":\"5103\"}');
INSERT INTO `verification_code` VALUES (29, '{\"code\":\"6863\"}');
INSERT INTO `verification_code` VALUES (30, '{\"code\":\"8176\"}');
INSERT INTO `verification_code` VALUES (31, '{\"code\":\"2384\"}');
INSERT INTO `verification_code` VALUES (32, '{\"code\":\"5202\"}');
INSERT INTO `verification_code` VALUES (33, '{\"code\":\"2613\"}');
INSERT INTO `verification_code` VALUES (34, '{\"code\":\"9592\"}');
INSERT INTO `verification_code` VALUES (35, '{\"code\":\"6547\"}');
INSERT INTO `verification_code` VALUES (36, '{\"code\":\"1473\"}');
INSERT INTO `verification_code` VALUES (37, '{\"code\":\"2397\"}');
INSERT INTO `verification_code` VALUES (38, '{\"code\":\"2939\"}');
INSERT INTO `verification_code` VALUES (39, '{\"code\":\"9486\"}');
INSERT INTO `verification_code` VALUES (40, '{\"code\":\"2442\"}');
INSERT INTO `verification_code` VALUES (41, '{\"code\":\"4339\"}');
INSERT INTO `verification_code` VALUES (42, '{\"code\":\"3061\"}');
INSERT INTO `verification_code` VALUES (43, '{\"code\":\"7663\"}');
INSERT INTO `verification_code` VALUES (44, '{\"code\":\"9373\"}');
INSERT INTO `verification_code` VALUES (45, '{\"code\":\"1308\"}');
INSERT INTO `verification_code` VALUES (46, '{\"code\":\"7120\"}');
INSERT INTO `verification_code` VALUES (47, '{\"code\":\"9795\"}');
INSERT INTO `verification_code` VALUES (48, '{\"code\":\"5477\"}');
INSERT INTO `verification_code` VALUES (49, '{\"code\":\"7858\"}');
INSERT INTO `verification_code` VALUES (50, '{\"code\":\"6058\"}');
INSERT INTO `verification_code` VALUES (51, '{\"code\":\"5230\"}');
INSERT INTO `verification_code` VALUES (52, '{\"code\":\"2976\"}');
INSERT INTO `verification_code` VALUES (53, '{\"code\":\"8650\"}');
INSERT INTO `verification_code` VALUES (54, '{\"code\":\"3782\"}');
INSERT INTO `verification_code` VALUES (55, '{\"code\":\"6432\"}');
INSERT INTO `verification_code` VALUES (56, '{\"code\":\"5311\"}');
INSERT INTO `verification_code` VALUES (57, '{\"code\":\"4828\"}');
INSERT INTO `verification_code` VALUES (58, '{\"code\":\"1767\"}');
INSERT INTO `verification_code` VALUES (59, '{\"code\":\"4158\"}');
INSERT INTO `verification_code` VALUES (60, '{\"code\":\"7307\"}');
INSERT INTO `verification_code` VALUES (61, '{\"code\":\"1230\"}');

SET FOREIGN_KEY_CHECKS = 1;
