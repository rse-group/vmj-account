module accountpl.account.overdraft {
	requires accountpl.account.core;
    exports accountpl.account.overdraft;

	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires vmj.auth;

	opens accountpl.account.overdraft to org.hibernate.orm.core, gson,vmj.hibernate.integrator;
}
