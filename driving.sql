/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50703
Source Host           : localhost:3306
Source Database       : driving

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2022-03-20 20:15:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', 'XX驾校', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '1', '长沙分公司', '1', '-1');
INSERT INTO `sys_dept` VALUES ('3', '1', '上海分公司', '2', '-1');
INSERT INTO `sys_dept` VALUES ('4', '3', '技术部', '0', '-1');
INSERT INTO `sys_dept` VALUES ('5', '3', '销售部', '1', '-1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('2', '0', '人员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('41', '0', '缴费管理', 'modules/business/pay.html', null, '1', 'fa fa-file-code-o', '6');
INSERT INTO `sys_menu` VALUES ('42', '0', '车辆管理', 'modules/business/car.html', null, '1', 'fa fa-file-code-o', '7');
INSERT INTO `sys_menu` VALUES ('43', '0', '体检信息', 'modules/business/medical.html', null, '1', 'fa fa-file-code-o', '8');
INSERT INTO `sys_menu` VALUES ('44', '43', '删除', null, 'business:medical:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('45', '43', '上传', null, 'business:medical:upload', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('46', '43', '下载', null, 'business:medical:down', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('47', '0', '预约练车', 'modules/business/appointment.html', null, '1', 'fa fa-file-code-o', '9');
INSERT INTO `sys_menu` VALUES ('48', '47', '查看', null, 'business:appointment:list,business:appointment:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('49', '47', '新增', null, 'business:appointment:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('50', '47', '修改', null, 'business:appointment:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('51', '47', '删除', null, 'business:appointment:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('52', '47', '预约', null, 'business:appointment:book', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('53', '47', '确认', null, 'business:appointment:confirm', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('54', '0', '我的预约', 'modules/business/myAppointment.html', null, '1', 'fa fa-file-code-o', '10');
INSERT INTO `sys_menu` VALUES ('55', '0', '成绩管理', 'modules/business/test.html', null, '1', 'fa fa-file-code-o', '11');
INSERT INTO `sys_menu` VALUES ('56', '55', '查看', null, 'business:test:list,business:test:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('57', '55', '新增', null, 'business:test:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('58', '55', '修改', null, 'business:test:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('59', '55', '删除', null, 'business:test:delete', '2', null, '6');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '教练', null, '1', '2022-03-19 11:30:40');
INSERT INTO `sys_role` VALUES ('2', '学员', null, '1', '2022-03-19 11:30:53');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('36', '1', '47');
INSERT INTO `sys_role_menu` VALUES ('37', '1', '48');
INSERT INTO `sys_role_menu` VALUES ('38', '1', '53');
INSERT INTO `sys_role_menu` VALUES ('39', '1', '55');
INSERT INTO `sys_role_menu` VALUES ('40', '1', '56');
INSERT INTO `sys_role_menu` VALUES ('41', '1', '57');
INSERT INTO `sys_role_menu` VALUES ('42', '1', '58');
INSERT INTO `sys_role_menu` VALUES ('43', '1', '59');
INSERT INTO `sys_role_menu` VALUES ('44', '2', '43');
INSERT INTO `sys_role_menu` VALUES ('45', '2', '44');
INSERT INTO `sys_role_menu` VALUES ('46', '2', '45');
INSERT INTO `sys_role_menu` VALUES ('47', '2', '47');
INSERT INTO `sys_role_menu` VALUES ('48', '2', '48');
INSERT INTO `sys_role_menu` VALUES ('49', '2', '52');
INSERT INTO `sys_role_menu` VALUES ('50', '2', '54');
INSERT INTO `sys_role_menu` VALUES ('51', '2', '55');
INSERT INTO `sys_role_menu` VALUES ('52', '2', '56');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `type` int(1) DEFAULT NULL COMMENT '类型 0教练 1学员',
  `plaintext` varchar(100) DEFAULT NULL COMMENT '密码明文',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@driving.io', '13612345678', '1', '1', '2016-11-11 11:11:11', null, '0', 'admin');
INSERT INTO `sys_user` VALUES ('2', 'zhangsan', '95bd4464e71ad08b590b3cf9b54cf90516b5fd837f2624ac7871d826be824486', 'zg3smm1U996SzN44Kaye', '156@qq.com', '13954623178', '1', '1', '2022-03-19 12:07:37', '张三', '0', '123456');
INSERT INTO `sys_user` VALUES ('5', 'lisi', '38aba2815c12c41195d5c20fda4db1d537c8839d7f172257eaea0b2003b35d25', 'Al1SVeqbXm3gPzvCtROZ', '51651@qq.com', '15956231478', '1', '1', '2022-03-19 12:46:11', '李四', '0', '123456');
INSERT INTO `sys_user` VALUES ('7', 'test', 'f77bae57e7b81010cb2abfa1563e38c2625bf96df6c3ce9abd51828c5c4c9b39', 'Jg9josQb8rBjyeG1RMkh', '1231@qq.com', '15166665555', '1', '1', '2022-03-19 12:47:26', '测试', '1', '123456');
INSERT INTO `sys_user` VALUES ('8', 'sy', '71771953b93ebb51c478ae166bfdd0bef44511b9d570238d8e72c374bb7da712', 'H2iLzVokQiHxKhtLG3pI', '1651@qq.com', '151666659844', '1', '1', '2022-03-19 16:08:33', '小于', '1', '654321');
INSERT INTO `sys_user` VALUES ('9', 'wangwu', '6d394d19593c70a9cb41dd9b41242c36a33876bdc67937c0574de9c7f67a4b22', '1BK7uCvHYKhScJWhzJbP', '156165@qq.com', '15478565324', '1', '1', '2022-03-20 16:02:14', '王五', '0', '123456');
INSERT INTO `sys_user` VALUES ('10', 'syy', 'afe1a3ea2064083b140959543e885551d39923ac1425c8577535f71d0c3ffbec', 'YDdorDNLzA75txWyKIwl', '1565@qq.com', '15689565645', '1', '1', '2022-03-20 16:02:35', '哇哈哈', '1', '123456');
INSERT INTO `sys_user` VALUES ('11', 'jiaolian1', 'b3f42954c6a02df356d29c4a062e6ea4a672dd6cc84b73a4273aec28f019a089', 'pggPcOQUX2ausmSDMKtH', '15615@qq.com', '15166663333', '1', '1', '2022-03-20 16:16:08', '教练1', '0', '123456');
INSERT INTO `sys_user` VALUES ('12', 'xueyuan1', 'aee5a7a9c48d9cb2c6f0c4b58b75f50ac9241270d47183f9849a9aef7644984b', 'j0fzkS9CjDaq8tY2etZW', '16515@qq.com', '15166665555', '1', '1', '2022-03-20 16:16:33', '学员1', '1', '123456');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '2', '1');
INSERT INTO `sys_user_role` VALUES ('5', '5', '1');
INSERT INTO `sys_user_role` VALUES ('15', '7', '2');
INSERT INTO `sys_user_role` VALUES ('16', '9', '1');
INSERT INTO `sys_user_role` VALUES ('17', '10', '2');
INSERT INTO `sys_user_role` VALUES ('18', '11', '1');
INSERT INTO `sys_user_role` VALUES ('19', '12', '2');
INSERT INTO `sys_user_role` VALUES ('27', '8', '2');

-- ----------------------------
-- Table structure for tb_appointment
-- ----------------------------
DROP TABLE IF EXISTS `tb_appointment`;
CREATE TABLE `tb_appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coach_id` bigint(20) DEFAULT NULL COMMENT '教练id',
  `car_id` bigint(20) DEFAULT NULL COMMENT '车辆id',
  `period` int(1) DEFAULT NULL COMMENT '时间段 0上午 1下午',
  `day` date DEFAULT NULL COMMENT '日期',
  `student_id` bigint(20) DEFAULT NULL COMMENT '学员id',
  `confirm` int(1) DEFAULT NULL COMMENT '是否确认 0学员未来；1学员应邀',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coach_id` (`coach_id`,`period`,`day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='预约管理';

-- ----------------------------
-- Records of tb_appointment
-- ----------------------------
INSERT INTO `tb_appointment` VALUES ('1', '2', '1', '0', '2022-03-21', '7', '1');
INSERT INTO `tb_appointment` VALUES ('2', '5', '2', '1', '2022-03-20', null, '0');
INSERT INTO `tb_appointment` VALUES ('4', '5', '2', '0', '2022-03-20', null, '0');
INSERT INTO `tb_appointment` VALUES ('7', '9', '3', '0', '2022-03-23', '10', '1');
INSERT INTO `tb_appointment` VALUES ('8', '11', '3', '0', '2022-03-23', '12', '1');

-- ----------------------------
-- Table structure for tb_car
-- ----------------------------
DROP TABLE IF EXISTS `tb_car`;
CREATE TABLE `tb_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` varchar(20) DEFAULT NULL COMMENT '车牌',
  `type` int(1) DEFAULT NULL COMMENT '类型 0手动 1自动',
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='车辆管理';

-- ----------------------------
-- Records of tb_car
-- ----------------------------
INSERT INTO `tb_car` VALUES ('1', '吉A-596KA', '1');
INSERT INTO `tb_car` VALUES ('2', '吉A-9956W', '0');
INSERT INTO `tb_car` VALUES ('3', '粤A-386KQ', '1');

-- ----------------------------
-- Table structure for tb_medical
-- ----------------------------
DROP TABLE IF EXISTS `tb_medical`;
CREATE TABLE `tb_medical` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file_path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='体检信息';

-- ----------------------------
-- Records of tb_medical
-- ----------------------------
INSERT INTO `tb_medical` VALUES ('11', '8', '2022-03-19 17:06:25', 'http://localhost:8080/driving-admin/profile//2022/03/19/05448d9d8d054f77b4526d803206bf82.docx', '小于--体检报告');
INSERT INTO `tb_medical` VALUES ('12', '7', '2022-03-19 17:11:14', 'http://localhost:8080/driving-admin/profile//2022/03/19/3a3daa79a2664c17a00d0dcf070e694e.docx', '测试--体检报告');
INSERT INTO `tb_medical` VALUES ('14', '10', '2022-03-20 16:04:03', 'http://localhost:8080/driving-admin/profile//2022/03/20/b0ab41bb455d406ebcc7d7583962662f.docx', '哇哈哈--体检报告');
INSERT INTO `tb_medical` VALUES ('16', '12', '2022-03-20 16:17:36', 'http://localhost:8080/driving-admin/profile//2022/03/20/754a3aa8d77d465492277b63c5187d00.docx', '学员1--体检报告');

-- ----------------------------
-- Table structure for tb_pay
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay`;
CREATE TABLE `tb_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `money` decimal(10,2) DEFAULT NULL COMMENT '缴费金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='缴费管理';

-- ----------------------------
-- Records of tb_pay
-- ----------------------------
INSERT INTO `tb_pay` VALUES ('2', '7', '2022-03-19 15:10:52', '2100.00');
INSERT INTO `tb_pay` VALUES ('3', '8', '2022-03-19 16:09:07', '2999.00');
INSERT INTO `tb_pay` VALUES ('4', '10', '2022-03-20 16:02:49', '4999.00');
INSERT INTO `tb_pay` VALUES ('5', '12', '2022-03-20 16:16:43', '3659.00');

-- ----------------------------
-- Table structure for tb_test
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) DEFAULT NULL COMMENT '学员id',
  `subject` varchar(10) DEFAULT NULL COMMENT '考试科目',
  `score` int(3) DEFAULT NULL COMMENT '分数',
  `create_time` datetime DEFAULT NULL COMMENT '考试时间',
  `coach_id` bigint(20) DEFAULT NULL COMMENT '教练id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `create_time` (`create_time`,`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='成绩管理';

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES ('17', '12', '科目一', '75', '2022-03-20 00:00:00', '2');
INSERT INTO `tb_test` VALUES ('19', '12', '科目一', '85', '2022-03-21 00:00:00', '2');
INSERT INTO `tb_test` VALUES ('20', '12', '科目二', '90', '2022-03-23 00:00:00', '2');
INSERT INTO `tb_test` VALUES ('21', '12', '科目三', '80', '2022-03-25 00:00:00', '2');
INSERT INTO `tb_test` VALUES ('22', '12', '科目三', '95', '2022-03-26 00:00:00', '2');
INSERT INTO `tb_test` VALUES ('23', '12', '科目四', '90', '2022-03-27 00:00:00', '2');
