/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.32 : Database - hmrefresh
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hmrefresh` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hmrefresh`;

/*Table structure for table `tb_course` */

DROP TABLE IF EXISTS `tb_course`;

CREATE TABLE `tb_course` (
  `cno` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(255) NOT NULL COMMENT '课程名字',
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_course` */

insert  into `tb_course`(`cno`,`cname`) values 
(1002,'Mysql'),
(1003,'英语'),
(1004,'高等数学'),
(1005,'ssm'),
(1007,'Java'),
(1008,'Mysql'),
(1009,'英语'),
(1010,'高等数学'),
(1011,'ssm');

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单名称，例如：用户列表',
  `path` varchar(255) NOT NULL COMMENT '菜单对应的url路径，例如：/user/list',
  `icon` varchar(255) DEFAULT '' COMMENT '菜单对应的element-ui中的图标，需要查看element-ui的icon列表来选择。例如：el-icon-user',
  `has_children` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有子菜单，默认为false',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单的id。默认为0，代表没有父菜单，本身就是一级菜单',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_menu` */

insert  into `tb_menu`(`id`,`name`,`path`,`icon`,`has_children`,`parent_id`,`create_time`,`update_time`) values 
(101,'首页','/index','el-icon-s-home',1,0,'2021-11-19 14:36:20','2021-11-19 16:13:26'),
(103,'权限管理','/auth','el-icon-s-custom',1,0,'2021-11-05 11:16:53','2021-11-05 11:16:53'),
(106,'菜单管理','/menu','',0,103,'2021-11-05 11:16:53','2021-11-05 11:16:53'),
(107,'角色管理','/role','',0,103,'2021-11-05 11:16:53','2021-11-05 11:16:53'),
(108,'用户管理','/user','',0,103,'2021-11-05 11:16:53','2021-11-05 11:16:53'),
(113,'学生管理','/class','el-icon-s-custom',1,0,'2021-11-16 08:38:47','2021-11-16 08:38:47'),
(114,'学生管理','/student','',0,113,'2021-11-16 08:39:20','2021-11-16 08:39:20'),
(115,'课程管理','/course','el-icon-s-management',1,0,'2021-11-18 09:40:51','2021-11-18 09:40:51'),
(116,'课程管理','/course','',0,115,'2021-11-18 09:44:17','2021-11-18 09:44:17'),
(117,'成绩管理','/score','el-icon-s-data',1,0,'2021-11-18 16:31:07','2021-11-18 16:31:07'),
(118,'成绩管理','/score','',0,117,'2021-11-18 16:31:27','2021-11-18 16:31:27'),
(119,'学生信息查看','/studentfind','',0,113,'2021-11-18 18:59:18','2021-11-18 18:59:18'),
(122,'课程信息查看','/coursefind','',0,115,'2021-11-19 14:03:47','2021-11-19 14:03:47'),
(123,'成绩信息查看','/scorefind','',0,117,'2021-11-19 14:10:48','2021-11-19 14:10:48');

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `tb_role` */

insert  into `tb_role`(`id`,`name`,`description`,`create_time`,`update_time`) values 
(10001,'超级管理员','具备完整权限的管理员','2021-11-05 11:17:50','2021-11-05 11:17:50'),
(10002,'系统管理员','管理系统权限信息','2021-11-09 20:56:08','2021-11-09 20:56:08'),
(10003,'老师','操作学生的基本功能','2021-11-11 01:16:58','2021-11-11 01:16:58'),
(10004,'学生','查看基本信息','2021-11-11 01:17:43','2021-11-11 01:17:43');

/*Table structure for table `tb_role_menu` */

DROP TABLE IF EXISTS `tb_role_menu`;

CREATE TABLE `tb_role_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `tb_role_menu` */

insert  into `tb_role_menu`(`menu_id`,`role_id`,`id`) values 
(101,10001,13),
(102,10001,14),
(104,10001,15),
(105,10001,16),
(103,10001,17),
(106,10001,18),
(107,10001,19),
(108,10001,20),
(109,10001,21),
(110,10001,22),
(114,10001,30),
(116,10001,33),
(118,10001,34),
(119,10001,41),
(121,10001,43),
(122,10001,44),
(123,10001,45),
(119,10004,51),
(122,10004,52),
(123,10004,53),
(120,10004,54),
(121,10004,55),
(113,10003,56),
(114,10003,57),
(119,10003,58),
(115,10003,59),
(116,10003,60),
(122,10003,61),
(117,10003,62),
(118,10003,63),
(123,10003,64),
(120,10003,65),
(121,10003,66),
(103,10002,67),
(106,10002,68),
(107,10002,69),
(108,10002,70),
(125,10001,71);

/*Table structure for table `tb_score` */

DROP TABLE IF EXISTS `tb_score`;

CREATE TABLE `tb_score` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` int(20) NOT NULL COMMENT '学生id',
  `cno` int(20) NOT NULL COMMENT '课程编号',
  `degree` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_score` */

insert  into `tb_score`(`id`,`sid`,`cno`,`degree`) values 
(1,1,1001,70),
(2,1,1002,85),
(3,1,1003,92),
(4,1,1004,86),
(5,2,1001,64),
(6,2,1002,85),
(7,2,1003,92),
(8,2,1004,86),
(10,1,1001,65),
(11,1,1002,85),
(12,1,1003,92),
(13,1,1004,86),
(14,2,1001,65),
(15,2,1002,85),
(16,2,1003,92),
(18,2,1004,88);

/*Table structure for table `tb_student` */

DROP TABLE IF EXISTS `tb_student`;

CREATE TABLE `tb_student` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_no` int(20) NOT NULL COMMENT '学籍号',
  `name` varchar(128) NOT NULL COMMENT '学生名称',
  `sex` varchar(255) NOT NULL COMMENT '性别',
  `card` varchar(128) NOT NULL COMMENT '身份证',
  `birth` varchar(255) NOT NULL COMMENT '出生年月',
  `enter_date` varchar(255) NOT NULL COMMENT '入学年份',
  `address` varchar(50) DEFAULT NULL COMMENT '家庭住址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_student` */

insert  into `tb_student`(`id`,`student_no`,`name`,`sex`,`card`,`birth`,`enter_date`,`address`) values 
(1,14335701,'张三0','男','46464846484684685','2001-01-10','2019-09-01','长沙市芙蓉区'),
(2,14335702,'张三1','女','464648464846846312','2001-02-10','2019-09-01','长沙市芙蓉区'),
(3,14335703,'张三2','男','464648464846846421','2001-03-10','2019-09-01','长沙市芙蓉区'),
(4,14335704,'张三3','女','4646484648468465532','2001-10-10','2019-09-01','长沙市芙蓉区'),
(5,14335705,'张三4','男','46464846484684611','2001-11-10','2019-09-01','长沙市芙蓉区'),
(6,14335706,'张三5','女','46464846484684622','2001-12-10','2019-09-01','长沙市芙蓉区'),
(7,14335707,'张三6','男','46464846484684555','2001-08-10','2019-09-01','长沙市芙蓉区'),
(8,14335708,'张三7','女','464648464846846532','2001-05-10','2019-09-01','长沙市芙蓉区'),
(9,14335709,'张三8','男','4646484648468465325','2001-06-10','2019-09-01','长沙市芙蓉区'),
(10,14335710,'张三9','女','4646484648468468535','2001-09-10','2019-09-01','长沙市芙蓉区'),
(11,14335711,'张三10','男','4646484648468468532','2001-08-10','2019-09-01','长沙市芙蓉区'),
(12,14335712,'张三11','男','46464846484684684124','2001-02-10','2019-09-01','长沙市芙蓉区'),
(13,14335713,'fasfsa','男','4894849489','2001-05-09','2019-09-01','发放机'),
(14,14335714,'发发','女','558999969','2002-02-01','2019-09-01','大家好费劲啊');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户名，长度在4~32位之间，可以包含字母、数字、下划线',
  `password` varchar(255) NOT NULL COMMENT '密码，长度大于6位，任意字符，保存时应该加密存储',
  `name` varchar(32) NOT NULL COMMENT '昵称，2~32位之间字符',
  `enable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用该用户，true或false',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`username`,`password`,`name`,`enable`,`create_time`,`last_login_time`) values 
(1,'liuyan','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','柳岩',1,'2021-11-11 00:59:50',NULL),
(2,'admin','ql4vrd7iq79pbczfup22@7811fea5294dc117546601fa5814e4df','超管',1,'2021-11-11 01:05:24','2021-11-19 16:45:27'),
(3,'jack','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','杰克',1,'2021-11-11 01:16:05','2021-11-19 16:38:18'),
(4,'rose','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','肉丝',1,'2021-11-11 01:16:05',NULL),
(6,'jerry','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','杰瑞',1,'2021-11-11 01:16:05',NULL),
(7,'white','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','怀特',1,'2021-11-11 01:16:05',NULL),
(8,'astrongman','eqbo5s3qfdh7tufs58gy@00fbe2ae7ee84eef851cea1e642e5c73','阿姆斯特朗',1,'2021-11-11 01:16:05',NULL),
(9,'laoshi','ph9bqvxovy6c2g2o7ypy@bf67bb22f77b7d60c0ced35245cc34ff','laoshi4',1,'2021-11-16 21:22:37','2021-11-19 16:38:42');

/*Table structure for table `tb_user_role` */

DROP TABLE IF EXISTS `tb_user_role`;

CREATE TABLE `tb_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `tb_user_role` */

insert  into `tb_user_role`(`id`,`user_id`,`role_id`) values 
(2,1,10001),
(3,1,10002),
(4,2,10001),
(5,2,10002),
(8,4,10003),
(9,9,10004),
(10,3,10003);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
