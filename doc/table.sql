CREATE TABLE `t_sys_log_response` (
  `id` varchar(50)  NOT NULL,
  `log_type` varchar(50)  DEFAULT NULL COMMENT '日志类型',
	`log_platform` varchar(50) DEFAULT NULL COMMENT '日志归属平台',
  `log_group_id` varchar(50) DEFAULT NULL COMMENT '日志分组',
  `log_create_datetime` datetime DEFAULT NULL COMMENT '日志时间',
  `log_content` text COMMENT '响应体',
  `status` int(11) DEFAULT NULL COMMENT '响应码',
  `headers` varchar(255) DEFAULT NULL COMMENT '响应头',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `t_sys_log_request` (
  `id` varchar(50)  NOT NULL ,
  `log_type` varchar(50)  DEFAULT NULL COMMENT '日志类型',
	`log_platform` varchar(50)  DEFAULT NULL COMMENT '日志归属平台',
  `log_group_id` varchar(50)  DEFAULT NULL COMMENT '日志分组',
  `log_create_datetime` datetime DEFAULT NULL COMMENT '日志时间',
  `log_content` text COMMENT '请求体',
  `method` varchar(50)  DEFAULT NULL COMMENT '请求方法',
  `path` varchar(50)  DEFAULT NULL COMMENT '请求路径',
  `headers` varchar(1000)  DEFAULT NULL COMMENT '请求头',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;