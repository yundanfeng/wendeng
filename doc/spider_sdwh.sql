/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.59 : Database - spider_sdwh
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spider_sdwh` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spider_sdwh`;

/*Table structure for table `channel` */

DROP TABLE IF EXISTS `channel`;

CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_url` varchar(255) DEFAULT NULL COMMENT '分类视频列表接口地址',
  `channel_id` varchar(32) DEFAULT NULL COMMENT '分类id',
  `channel_name` varchar(32) DEFAULT NULL COMMENT '分类名称',
  `column_id` int(11) DEFAULT NULL COMMENT '所属栏目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `channel` */

insert  into `channel`(`id`,`api_url`,`channel_id`,`channel_name`,`column_id`) values (1,'https://www.xuexi.cn/lgdata/1novbsbi47k.json?_st=25965045','1novbsbi47k','第一频道-重要活动视频专辑',1),(2,'https://www.xuexi.cn/lgdata/1koo357ronk.json?_st=25965047','1koo357ronk','第一频道-学习专题报告',1),(3,'https://www.xuexi.cn/lgdata/1742g60067k.json?_st=25965055','1742g60067k','第一频道-学习新视界',1),(4,'https://www.xuexi.cn/lgdata/13n3ido517l.json?_st=25965056','13n3ido517l','第一频道-十九大报告视频',1),(5,'https://www.xuexi.cn/lgdata/17th9fq5c7l.json?_st=25965056','17th9fq5c7l','第一频道-新闻联播',1),(6,'https://www.xuexi.cn/lgdata/vc9n1ga0nl.json?_st=25965059','vc9n1ga0nl','党史频道-永远的丰碑',2),(7,'https://www.xuexi.cn/lgdata/13jjb7vvp7l.json?_st=25965060','13jjb7vvp7l','党史频道-红色记忆',2),(8,'https://www.xuexi.cn/lgdata/cim91v4tho0.json?_st=25965061','cim91v4tho0','人物频道-新时代楷模',3),(9,'https://www.xuexi.cn/lgdata/139qnt854nl.json?_st=25965062','139qnt854nl','人物频道-新时代的奋斗者',3),(10,'https://www.xuexi.cn/lgdata/1okif07b5nl.json?_st=25965062','1okif07b5nl','人物频道-身边的感动',3),(11,'https://www.xuexi.cn/lgdata/f79hr84v5f3.json?_st=25965062','f79hr84v5f3','人物频道-艺坛大家',3),(12,'https://www.xuexi.cn/lgdata/134v6tkjknl.json?_st=25965063','134v6tkjknl','文艺频道-音乐汇',4),(13,'https://www.xuexi.cn/lgdata/1f8iooppm7l.json?_st=25965064','1f8iooppm7l','文艺频道-文艺广场',4),(14,'https://www.xuexi.cn/lgdata/1nide86n3nl.json?_st=25965064','1nide86n3nl','文艺频道-戏曲',4),(15,'https://www.xuexi.cn/lgdata/t6puhl4knl.json?_st=25965064','t6puhl4knl','文艺频道-戏剧',4),(16,'https://www.xuexi.cn/lgdata/1bfcj7u3pnl.json?_st=25965064','1bfcj7u3pnl','文艺频道-文化大观',4),(17,'https://www.xuexi.cn/lgdata/14s4462g9nl.json?_st=25965066','14s4462g9nl','法制频道-专题片',5),(18,'https://www.xuexi.cn/lgdata/v3klp6fr7l.json?_st=25965067','v3klp6fr7l','法制频道-法律讲堂',5),(19,'https://www.xuexi.cn/lgdata/11otarnmh7l.json?_st=25965067','11otarnmh7l','军事天地-专题片',6),(20,'https://www.xuexi.cn/lgdata/16421k8267l.json?_st=25965068','16421k8267l','军事天地-军事微视频',6),(21,'https://www.xuexi.cn/lgdata/eta8vnluqmd.json?_st=25965068','eta8vnluqmd','军事天地-军事科技前沿',6),(22,'https://www.xuexi.cn/lgdata/16cqa8jnh7l.json?_st=25965068','16cqa8jnh7l','军事天地-军营MV',6),(23,'https://www.xuexi.cn/lgdata/16e0lo2fg7l.json?_st=25965068','16e0lo2fg7l','军事天地-军事纪录片',6),(24,'https://www.xuexi.cn/lgdata/u1cia4cg7l.json?_st=25965068','u1cia4cg7l','军事天地-战争片',6),(25,'https://www.xuexi.cn/lgdata/1nbudo99t7l.json?_st=25965072','1nbudo99t7l','自然频道-美丽中国·视频',7),(26,'https://www.xuexi.cn/lgdata/aifsg6f5h96.json?_st=25965072','aifsg6f5h96','自然频道-神州速览',7),(27,'https://www.xuexi.cn/lgdata/18rkaul9h7l.json?_st=25965073','18rkaul9h7l','影视频道-电影',8),(28,'https://www.xuexi.cn/lgdata/109tvcosfnl.json?_st=25965073','109tvcosfnl','影视频道-电视剧',8),(29,'https://www.xuexi.cn/lgdata/17fsu5j4hnl.json?_st=25965073','17fsu5j4hnl','影视频道-纪录片',8),(30,'https://www.xuexi.cn/lgdata/1am3asi2enl.json?_st=25965073','1am3asi2enl','影视频道-微电影',8),(31,'https://www.xuexi.cn/lgdata/97drfsf1d2d.json?_st=25965073','97drfsf1d2d','影视频道-电视专题片',8);

/*Table structure for table `column` */

DROP TABLE IF EXISTS `column`;

CREATE TABLE `column` (
  `id` int(11) NOT NULL COMMENT '栏目id',
  `column_name` varchar(32) DEFAULT NULL COMMENT '栏目名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `column` */

insert  into `column`(`id`,`column_name`) values (1,'第一频道'),(2,'党史频道'),(3,'人物频道'),(4,'文艺频道'),(5,'法制频道'),(6,'军事天地'),(7,'自然频道'),(8,'影视频道');

/*Table structure for table `manager_info` */

DROP TABLE IF EXISTS `manager_info`;

CREATE TABLE `manager_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `username` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `manager_info` */

insert  into `manager_info`(`id`,`name`,`password`,`username`) values (1,'超级管理员','63a9f0ea7bb98050796b649e85481845','admin');

/*Table structure for table `manager_info_roles` */

DROP TABLE IF EXISTS `manager_info_roles`;

CREATE TABLE `manager_info_roles` (
  `manager_info_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FKntp3ihyxiaqw8p4uihfrge8tp` (`roles_id`),
  CONSTRAINT `FKntp3ihyxiaqw8p4uihfrge8tp` FOREIGN KEY (`roles_id`) REFERENCES `role_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `manager_info_roles` */

insert  into `manager_info_roles`(`manager_info_id`,`roles_id`) values (1,4),(1,5);

/*Table structure for table `role_info` */

DROP TABLE IF EXISTS `role_info`;

CREATE TABLE `role_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `role_info` */

insert  into `role_info`(`id`,`name`,`url`) values (4,'用户管理','/manager/**'),(5,'媒资管理','/news/**');

/*Table structure for table `video_data` */

DROP TABLE IF EXISTS `video_data`;

CREATE TABLE `video_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_id` varchar(32) DEFAULT NULL COMMENT '数据id',
  `item_id` varchar(32) NOT NULL,
  `thumb_image` varchar(255) DEFAULT NULL COMMENT '封面访问路径',
  `title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `channel_id` varchar(32) DEFAULT NULL COMMENT '分类id',
  `html_url` varchar(255) DEFAULT NULL COMMENT '视频详情页面',
  `api_url` varchar(255) DEFAULT NULL COMMENT '视频详情接口地址',
  `oss_url` varchar(255) DEFAULT NULL COMMENT '视频访问地址',
  `content` text COMMENT '文本内容',
  `source` varchar(20) DEFAULT NULL COMMENT '发布来源',
  `editor` varchar(20) DEFAULT NULL COMMENT '编辑',
  `is_sync` tinyint(1) DEFAULT '0' COMMENT '同步状态，0未同步，1已同步',
  `publish_time` varchar(32) DEFAULT NULL COMMENT '发布时间',
  `update_time` varchar(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `video_data` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
