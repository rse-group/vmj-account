module vmj.routing.route {
    exports vmj.routing.route;

    requires vmj.object.mapper;
    requires jdk.httpserver;
    requires prices.auth;
    requires transitive prices.auth.vmj;
    requires transitive gson;
    requires transitive sqlite.jdbc;
}
