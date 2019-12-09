/*
Navicat MySQL Data Transfer

Source Server         : 192.168.112.101
Source Server Version : 50727
Source Host           : 192.168.112.101:3306
Source Database       : example

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2019-12-09 22:57:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for factor
-- ----------------------------
DROP TABLE IF EXISTS `factor`;
CREATE TABLE `factor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `humidity` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `temperature` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of factor
-- ----------------------------

-- ----------------------------
-- Table structure for news_item
-- ----------------------------
DROP TABLE IF EXISTS `news_item`;
CREATE TABLE `news_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `text` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_item
-- ----------------------------

-- ----------------------------
-- Table structure for present_situation
-- ----------------------------
DROP TABLE IF EXISTS `present_situation`;
CREATE TABLE `present_situation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `text` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of present_situation
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `atom_url` varchar(255) DEFAULT NULL,
  `crud_name` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs2byvqo0b2enh3rltln5mmvyl` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '2019-12-07 11:28:21', '2019-12-07 11:28:21', '/', '', 'Dir_Module', '', '系==统', '系统根资源', '/', null);
INSERT INTO `resource` VALUES ('3', '2019-12-07 12:22:39', '2019-12-09 08:51:44', 'rbac', '', 'Dir_Module', '', 'RBAC权限', 'RBAC权限管理', '/rbac', '1');
INSERT INTO `resource` VALUES ('4', '2019-12-07 12:24:26', '2019-12-07 12:24:26', 'user', '', 'Menu_Entity', '', '用户管理', '用户管理', '/rbac/user', '3');
INSERT INTO `resource` VALUES ('5', '2019-12-07 12:27:30', '2019-12-07 12:27:30', 'role', '', 'Menu_Entity', '', '角色管理', '角色管理', '/rbac/role', '3');
INSERT INTO `resource` VALUES ('6', '2019-12-07 12:29:53', '2019-12-07 12:29:53', '', 'read', 'Button_Method', 'GET', '查询用户', '查询用户', '/rbac/user', '4');
INSERT INTO `resource` VALUES ('7', '2019-12-07 12:30:45', '2019-12-07 12:30:45', '', 'create', 'Button_Method', 'POST', '增加用户', '增加用户', '/rbac/user', '4');
INSERT INTO `resource` VALUES ('8', '2019-12-07 12:31:24', '2019-12-07 12:31:24', '{*}', 'update', 'Button_Method', 'PUT', '修改用户', '修改用户', '/rbac/user/{*}', '4');
INSERT INTO `resource` VALUES ('9', '2019-12-07 12:32:06', '2019-12-08 07:01:54', '{*}', 'delete', 'Button_Method', 'DETELE', '删除用户1', '删除用户', '/rbac/user/{*}', '4');
INSERT INTO `resource` VALUES ('10', '2019-12-07 13:38:27', '2019-12-07 13:38:27', '', 'create', 'Button_Method', 'POST', '增加角色', '增加角色', '/rbac/role', '5');
INSERT INTO `resource` VALUES ('11', '2019-12-08 10:43:16', '2019-12-08 10:43:16', '{*}', 'read', 'Button_Method', 'GET', '查询单个角色', '查询单个角色', '/rbac/role/{*}', '5');
INSERT INTO `resource` VALUES ('12', '2019-12-08 11:29:34', '2019-12-08 11:29:34', 'resource', '', 'Menu_Entity', '', '资源管理', '资源管理', '/rbac/resource', '3');
INSERT INTO `resource` VALUES ('13', '2019-12-08 11:30:07', '2019-12-08 11:30:07', '', 'create', 'Button_Method', 'POST', '添加资源', '添加资源', '/rbac/resource', '12');

-- ----------------------------
-- Table structure for resource_children
-- ----------------------------
DROP TABLE IF EXISTS `resource_children`;
CREATE TABLE `resource_children` (
  `resource_id` int(11) NOT NULL,
  `children_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ftjvwfrlndfh3cjgwh0e5acvd` (`children_id`),
  KEY `FK7eaoy9yj8m44p4s75bdjepieu` (`resource_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource_children
-- ----------------------------
INSERT INTO `resource_children` VALUES ('3', '12');
INSERT INTO `resource_children` VALUES ('3', '5');
INSERT INTO `resource_children` VALUES ('3', '4');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cnname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('2', '2019-10-14 08:56:05', '2019-10-14 08:56:05', '系统管理员', 'admin', '系统管理员');
INSERT INTO `role` VALUES ('3', '2019-10-14 08:56:29', '2019-10-14 08:56:29', '系统审计员', 'audit', '系统审计员');
INSERT INTO `role` VALUES ('4', '2019-12-06 08:15:26', '2019-12-06 08:53:10', '统计员', 'counter', '统计员');

-- ----------------------------
-- Table structure for role_resources
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `role_id` int(11) NOT NULL,
  `resources_id` int(11) NOT NULL,
  KEY `FK6cbtybbqlpluxeas15dsl2nir` (`resources_id`),
  KEY `FK7k960kk6pu1pwsk7ml4hycp53` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resources
-- ----------------------------
INSERT INTO `role_resources` VALUES ('2', '1');
INSERT INTO `role_resources` VALUES ('2', '3');
INSERT INTO `role_resources` VALUES ('2', '5');
INSERT INTO `role_resources` VALUES ('2', '11');
INSERT INTO `role_resources` VALUES ('2', '10');
INSERT INTO `role_resources` VALUES ('2', '4');
INSERT INTO `role_resources` VALUES ('2', '9');
INSERT INTO `role_resources` VALUES ('2', '8');
INSERT INTO `role_resources` VALUES ('2', '7');
INSERT INTO `role_resources` VALUES ('2', '6');
INSERT INTO `role_resources` VALUES ('3', '10');
INSERT INTO `role_resources` VALUES ('3', '11');
INSERT INTO `role_resources` VALUES ('3', '5');
INSERT INTO `role_resources` VALUES ('3', '13');
INSERT INTO `role_resources` VALUES ('3', '12');
INSERT INTO `role_resources` VALUES ('3', '3');
INSERT INTO `role_resources` VALUES ('3', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cnname` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('9', '2019-12-08 11:27:47', '2019-12-08 11:27:47', '管理者A', '\0', '$2a$10$jOBXcE3yRXM3Iy5ysH9gl.UzG.e6ciYXOdjArJKAlAjXlP2HlVH3y', '管理者A', 'admin');
INSERT INTO `user` VALUES ('10', '2019-12-08 11:28:13', '2019-12-08 11:28:13', '审计者B', '\0', '$2a$10$w6ImTpCLghMwa0ewW3pFYeZneYoqJSop8sZMwCzW0NUZBqwuwEBYu', '123456', 'audit');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('9', '2');
INSERT INTO `user_roles` VALUES ('10', '3');
