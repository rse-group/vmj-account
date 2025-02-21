module accountpl.account.interest {
	requires accountpl.account.core;
    exports accountpl.account.interest;

	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires prices.auth.vmj;

	opens accountpl.account.interest to org.hibernate.orm.core, gson, vmj.hibernate.integrator;
}
