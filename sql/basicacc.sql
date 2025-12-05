INSERT INTO account_comp (id_account, balance, objectname, modulesequence) VALUES
(123456, 20000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(78910, 500000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(23579, 1000000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(23232, 3000000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(989898, 30000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(567890, 40000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(901234, 800000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(678901, 2000000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(667788, 40000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(2033260512, 150000, 'accountpl.account.overdraft.AccountImpl', NULL),
(889900, 150000, 'accountpl.account.overdraft.AccountImpl', 'account_impl, account_overdraft'),
(1008571206, 3500000, 'accountpl.account.overdraft.AccountImpl', NULL),
(990011, 3500000, 'accountpl.account.overdraft.AccountImpl', 'account_impl, account_overdraft'),
(101112, 70000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(246802, 120000, 'accountpl.account.core.AccountImpl', 'account_impl'),
(357913, 320000, 'accountpl.account.core.AccountImpl', 'account_impl');


INSERT INTO account_impl (id_account) VALUES
(123456),
(78910),
(23579),
(23232),
(989898),
(567890),
(901234),
(678901),
(667788),
(889900),
(990011),
(101112),
(246802),
(357913);

INSERT INTO account_overdraft (overdraft_limit, id_account, record_id_account, recordname, base_component_id) VALUES
(20000, 2033260512, 889900, 'accountpl.account.core.AccountImpl', 889900),
(50000, 1008571206, 990011, 'accountpl.account.core.AccountImpl', 990011);

