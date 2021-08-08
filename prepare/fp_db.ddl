SET FOREIGN_KEY_CHECKS = 0;

-- fp_db.`user` definition
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS wallet_account;
DROP TABLE IF EXISTS wallet_credit_transaction;
DROP VIEW IF EXISTS view_wallet_credit_transaction_summary;

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

CREATE VIEW view_wallet_credit_transaction_summary AS
(
SELECT credit.user_id,
       credit.user_reference,
       IFNULL(credit.total_credit, 0)                                  AS 'total_credit',
       IFNULL(debit.total_debit, 0)                                    AS 'total_debit',
       (IFNULL(credit.total_credit, 0) - IFNULL(debit.total_debit, 0)) AS 'balance'
FROM (
         SELECT u.id AS 'user_id', u.user_reference, SUM(wct.amount) AS 'total_credit'
         FROM wallet_credit_transaction wct
                  INNER JOIN wallet_account wc ON wct.wallet_account_id = wc.id AND wc.enabled = true
                  INNER JOIN user u ON wc.user_id = u.id AND u.enabled = true
         WHERE wct.account_type = 'CREDIT'
         GROUP BY u.id
     ) AS credit
         LEFT JOIN
     (
         SELECT u.id AS 'user_id', u.user_reference, SUM(wct.amount) AS 'total_debit'
         FROM wallet_credit_transaction wct
                  INNER JOIN wallet_account wc ON wct.wallet_account_id = wc.id AND wc.enabled = true
                  INNER JOIN user u ON wc.user_id = u.id AND u.enabled = true
         WHERE wct.account_type = 'DEBIT'
         GROUP BY u.id
     ) AS debit
     ON credit.user_id = debit.user_id
    );
SET FOREIGN_KEY_CHECKS = 1;
