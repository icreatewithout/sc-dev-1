/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : sc_file

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 19/05/2022 21:03:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sc_file
-- ----------------------------
DROP TABLE IF EXISTS `sc_file`;
CREATE TABLE `sc_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `create_by` bigint DEFAULT NULL,
  `del` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `biz_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bucket` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_type` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ext_name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `f_path` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=972567733310849025 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sc_file
-- ----------------------------
BEGIN;
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972529793331363840, '2022-05-07 16:06:11.461000', 962305787315617792, '0', NULL, '2022-05-07 16:06:11.461000', 962305787315617792, NULL, NULL, 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972533072383705088, '2022-05-07 16:19:13.247000', 962305787315617792, '0', NULL, '2022-05-07 16:19:13.247000', 962305787315617792, NULL, NULL, 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972533939820298240, '2022-05-07 16:22:40.060000', 962305787315617792, '0', NULL, '2022-05-07 16:22:40.060000', 962305787315617792, NULL, NULL, 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972544231597932544, '2022-05-07 17:03:33.809000', 962305787315617792, '0', NULL, '2022-05-07 17:03:33.809000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972546664147451904, '2022-05-07 17:13:13.773000', 962305787315617792, '0', NULL, '2022-05-07 17:13:13.773000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972550432947699712, '2022-05-07 17:28:12.324000', 962305787315617792, '0', NULL, '2022-05-07 17:28:12.324000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972550456247058432, '2022-05-07 17:28:17.883000', 962305787315617792, '0', NULL, '2022-05-07 17:28:17.883000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972559571455311872, '2022-05-07 18:04:31.113000', 962305787315617792, '0', NULL, '2022-05-07 18:04:31.113000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972559693740244992, '2022-05-07 18:05:00.273000', 962305787315617792, '0', NULL, '2022-05-07 18:05:00.273000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972559856420519936, '2022-05-07 18:05:39.060000', 962305787315617792, '0', NULL, '2022-05-07 18:05:39.060000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972560269567852544, '2022-05-07 18:07:17.561000', 962305787315617792, '0', NULL, '2022-05-07 18:07:17.561000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972561192050491392, '2022-05-07 18:10:57.498000', 962305787315617792, '0', NULL, '2022-05-07 18:10:57.498000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972561222178177024, '2022-05-07 18:11:04.682000', 962305787315617792, '0', NULL, '2022-05-07 18:11:04.682000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972563699724189696, '2022-05-07 18:20:55.375000', 962305787315617792, '0', NULL, '2022-05-07 18:20:55.375000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972563869555752960, '2022-05-07 18:21:35.865000', 962305787315617792, '0', NULL, '2022-05-07 18:21:35.865000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
INSERT INTO `sc_file` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `biz_name`, `bucket`, `content_type`, `ext_name`, `f_path`, `file_name`, `user_id`) VALUES (972567733310849024, '2022-05-07 18:36:57.053000', 962305787315617792, '0', NULL, '2022-05-07 18:36:57.053000', 962305787315617792, NULL, 'sc-dev-bucket', 'image/png', '.png', '20220507/962305787315617792/', 'WechatIMG16', 962305787315617792);
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'branch transaction id',
  `xid` varchar(128) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `id` (`branch_id`,`xid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='AT transaction mode undo table';

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
