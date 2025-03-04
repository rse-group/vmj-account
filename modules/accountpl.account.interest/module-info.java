module accountpl.account.interest {
	requires accountpl.account.core;
    exports accountpl.account.interest;

	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires vmj.auth;

	opens accountpl.account.interest to org.hibernate.orm.core, gson, vmj.hibernate.integrator;
}
