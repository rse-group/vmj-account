module prices.auth.vmj {
    exports prices.auth.vmj;
    exports prices.auth.vmj.annotations;
    exports prices.auth.vmj.enums;
    exports prices.auth.vmj.storagestrategy;

    requires jdk.httpserver;
    requires transitive prices.auth;
    requires transitive vmj.object.mapper;
}
