CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`      bigint       NOT NULL auto_increment PRIMARY KEY,
    `name`         VARCHAR(255) NOT NULL,
    `email`        varchar(255) NOT NULL,
    `phone_number` varchar(20)  NOT NULL COMMENT '010-0000-0000',
    `password`     VARCHAR(255) NOT NULL,
    `created_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE IF NOT EXISTS `book`
(
    `book_id`      bigint       NOT NULL auto_increment PRIMARY KEY,
    `title`        varchar(255) NOT NULL,
    `isbn`         varchar(20)  NOT NULL,
    `rental_price` int(10)      NOT NULL DEFAULT 0,
    `user_id`      bigint       NOT NULL,
    `rental_count` int          NOT NULL DEFAULT 0,
    `created_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `rental_log`
(
    `log_id`      bigint    NOT NULL auto_increment PRIMARY KEY,
    `user_id`     bigint    NOT NULL,
    `book_id`     bigint    NOT NULL,
    `rental_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `return_date` TIMESTAMP NULL,
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
