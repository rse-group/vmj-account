module prices.auth {
    exports prices.auth.core;
    exports prices.auth.core.exceptions;
    exports prices.auth.utils;

    exports prices.auth.providers;
    exports prices.auth.providers.manual;
    exports prices.auth.providers.oauth2.auth0;
    exports prices.auth.providers.oauth2.google;
    exports prices.auth.providers.oauth2.facebook;

    requires java.logging;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.codec;
    requires gson;
    requires org.json;
    requires auth0.jwt;
    requires auth0.jwk;
    requires google.api;
    requires google.http;
    requires google.http.gson;
    requires google.oauth;
}
