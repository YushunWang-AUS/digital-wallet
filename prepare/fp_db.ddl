-- fp_db.`user` definition

CREATE TABLE `user`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_reference` varchar(128) NOT NULL UNIQUE,
    `email`          varchar(64)  NOT NULL,
    `first_name`     varchar(64)  NOT NULL,
    `last_name`      varchar(64)  NOT NULL,
    `username`       varchar(64)  NOT NULL UNIQUE,
    `enabled`        boolean default TRUE,
    PRIMARY KEY (`id`)
);

-- fp_db.wallet_account definition

CREATE TABLE `wallet_account`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(20) NOT NULL,
    `created_at` datetime   NOT NULL,
    `enabled`    boolean default TRUE,
    PRIMARY KEY (`id`),
    CONSTRAINT `wallet_account_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

-- fp_db.wallet_credit_transaction definition

CREATE TABLE `wallet_credit_transaction`
(
    `id`                    bigint(20)    NOT NULL AUTO_INCREMENT,
    `transaction_reference` varchar(128)  NOT NULL UNIQUE,
    `wallet_account_id`     bigint(20)    NOT NULL,
    `amount`                bigint(20)    NOT NULL,
    `currency`              varchar(16)   NOT NULL,
    `transaction_type`      varchar(20)   NOT NULL,
    `method`                varchar(32)   NOT NULL,
    `account_type`          varchar(32)   NOT NULL,
    `description`           varchar(2048) NOT NULL,
    `created_at`            datetime      NOT NULL,
    `updated_at`            datetime      NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `wallet_credit_transaction_fk1` FOREIGN KEY (`wallet_account_id`) REFERENCES `wallet_account` (`id`)
);