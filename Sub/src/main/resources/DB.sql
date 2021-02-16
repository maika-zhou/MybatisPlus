CREATE TABLE `t_user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT '0' COMMENT '1代表删除，0代表未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8