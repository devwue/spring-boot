CREATE TABLE if not exists `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `created_at` timestamp NOT NULL,
                         `updated_at` timestamp NOT NULL default current_timestamp,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY (`email`)
) ENGINE=InnoDB comment '사용자';

CREATE TABLE if not exists `service_roles` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `user_id` varchar(255) DEFAULT NULL,
                                       `name` varchar(255) DEFAULT NULL,
                                       `created_at` timestamp NOT NULL,
                                       `updated_at` timestamp NOT NULL default current_timestamp,
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB comment '서비스 권한';
