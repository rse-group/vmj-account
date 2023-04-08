INSERT INTO account_comp (id_account) VALUES (1);
INSERT INTO account_comp (id_account) VALUES (2);
INSERT INTO account_comp (id_account) VALUES (3);
INSERT INTO account_comp (id_account) VALUES (4);

INSERT INTO account_impl(balance, overdraft_limit, id_account) VALUES (100000, 0, 1);
INSERT INTO account_impl(balance, overdraft_limit, id_account) VALUES (200000, 0, 2);
INSERT INTO account_impl(balance, overdraft_limit, id_account) VALUES (0, 0, 3);
INSERT INTO account_impl(balance, overdraft_limit, id_account) VALUES (300000, 0, 4);

