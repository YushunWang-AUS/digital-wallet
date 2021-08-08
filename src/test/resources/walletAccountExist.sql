INSERT INTO fp_db.`user` (id, user_reference,email,first_name,last_name,username,enabled)
VALUES (999, '449416d8-ec3c-4c0b-a326-e2cfaadaa3a6','neol@gmail.com','Neol','Buyer','neol',1);

INSERT INTO wallet_account (id, user_id, created_at, enabled)
VALUES(1, 999, NOW(), 1);

