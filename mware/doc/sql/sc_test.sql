/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : sc_test

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 19/05/2022 21:03:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sc_seata_test
-- ----------------------------
DROP TABLE IF EXISTS `sc_seata_test`;
CREATE TABLE `sc_seata_test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `create_by` bigint DEFAULT NULL,
  `del` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `val` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=974098010868809729 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sc_seata_test
-- ----------------------------
BEGIN;
INSERT INTO `sc_seata_test` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `name`, `val`) VALUES (974097957181718528, '2022-05-11 23:57:30.847000', NULL, '0', NULL, '2022-05-11 23:57:30.847000', NULL, '1', 'hello');
INSERT INTO `sc_seata_test` (`id`, `create_time`, `create_by`, `del`, `remark`, `update_time`, `update_by`, `name`, `val`) VALUES (974098010868809728, '2022-05-11 23:57:43.657000', NULL, '0', NULL, '2022-05-11 23:57:43.657000', NULL, '12', 'hello');
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
) ENGINE=InnoDB AUTO_INCREMENT=9268712349130843 DEFAULT CHARSET=utf8mb3 COMMENT='AT transaction mode undo table';

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
