CREATE TABLE `t_system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户类型',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(256) NOT NULL DEFAULT 'abc123' COMMENT '密码',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-启用（恢复） 1-停用（冻结）',
  `is_root` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是超管 1.是 0.不是',
  `remark` varchar(64) NOT NULL DEFAULT '' COMMENT '账号备注',
  `create_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最近登录时间',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `identity` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='系统用户';

