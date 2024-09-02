-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: task-management
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.24.04.1

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `role` enum('GUEST', 'MEMBER', 'MANAGER') DEFAULT 'member' COMMENT '用户角色',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

DROP TABLE IF EXISTS `user_tokens`;
CREATE TABLE `user_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `token` varchar(255) NOT NULL COMMENT '用户Token',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户令牌';


ALTER TABLE users
MODIFY COLUMN password VARCHAR(60) NOT NULL COMMENT '密码';
