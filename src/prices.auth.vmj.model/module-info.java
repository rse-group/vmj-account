module prices.auth.vmj.model {
    exports prices.auth.vmj.model;
    exports prices.auth.vmj.model.core;
    exports prices.auth.vmj.model.passworded;

    requires jdk.httpserver;
    requires java.logging;
    requires vmj.hibernate.integrator;
    requires transitive prices.auth;
    requires transitive prices.auth.vmj;
    requires transitive vmj.object.mapper;
    requires transitive vmj.routing.route;
}
