module accountpl.account.core {
  	exports accountpl.account;
    exports accountpl.account.core;

    requires vmj.routing.route;
    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
    requires vmj.hibernate.integrator;
    requires java.logging;
    requires prices.auth.vmj;

    opens accountpl.account.core to org.hibernate.orm.core, gson;
}
