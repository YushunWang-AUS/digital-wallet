-- fp_db.`user` definition

CREATE TABLE `user`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT,
    `email`      varchar(64) NOT NULL,
    `first_name` varchar(32) NOT NULL,
    `last_name`  varchar(32) NOT NULL,
    `username`   varchar(60) NOT NULL,
    `enabled`    boolean default TRUE,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
);

-- fp_db.wallet_account definition

CREATE TABLE `wallet_account`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(20) NOT NULL,
    `date_created` datetime   NOT NULL,
    `enabled`      boolean default TRUE,
    PRIMARY KEY (`id`),
    CONSTRAINT `wallet_account_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

-- fp_db.wallet_credit_transaction definition

CREATE TABLE `wallet_credit_transaction`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `wallet_account_id` bigint(20) NOT NULL,
    `amount`            bigint(20) NOT NULL,
    `date_created`      datetime   NOT NULL,
    `expiry`            datetime DEFAULT NULL,
    `used`              boolean  default FALSE,
    PRIMARY KEY (`id`),
    INDEX (wallet_account_id, used),
    CONSTRAINT `wallet_credit_transaction_fk1` FOREIGN KEY (`wallet_account_id`) REFERENCES `wallet_account` (`id`)
);