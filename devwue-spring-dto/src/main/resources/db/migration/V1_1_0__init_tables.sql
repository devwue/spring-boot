CREATE DATABASE if not exists devwue character set utf8mb4 collate utf8mb4_unicode_ci;
use devwue;

CREATE TABLE `posts` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `type` varchar(255) DEFAULT "blog" not null,
                        `post_no` varchar(255) DEFAULT NULL,
                        `contents` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `created_at` timestamp NOT NULL,
                        `updated_at` timestamp NOT NULL default current_timestamp,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB comment '블로그 포스팅';
