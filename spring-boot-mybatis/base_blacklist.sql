/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50633
Source Host           : localhost:3306
Source Database       : oneking_member

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-12-29 15:44:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_blacklist`
-- ----------------------------
DROP TABLE IF EXISTS `base_blacklist`;
CREATE TABLE `base_blacklist` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `host` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `status` bit(1) DEFAULT NULL COMMENT '状态 0：停用 1：使用',
  `description` varchar(100) DEFAULT NULL COMMENT '说明',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户登录名称',
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `crt_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `crt_host` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `upd_time` datetime DEFAULT NULL,
  `upd_user` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `upd_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `upd_host` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `de_type` tinyint(255) DEFAULT '0',
  `platform` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_blacklist
-- ----------------------------
INSERT INTO `base_blacklist` VALUES ('8', '192.168.0.166', '', '平凡的发送请求11', null, 'zhangshan', '2017-08-21 17:23:13', '1', '管理员', '0:0:0:0:0:0:0:1', '2017-08-21 17:26:35', '1', '管理员', '0:0:0:0:0:0:0:1', '0', '1');
INSERT INTO `base_blacklist` VALUES ('9', 'fds1111', '', 'fdsa', null, 'fdsa', '2017-09-11 16:13:20', '351eaa033a1a4bcaa35703ece790af08', 'admin', '0:0:0:0:0:0:0:1', '2017-09-11 16:20:17', '351eaa033a1a4bcaa35703ece790af08', 'admin', '0:0:0:0:0:0:0:1', '1', '1');
