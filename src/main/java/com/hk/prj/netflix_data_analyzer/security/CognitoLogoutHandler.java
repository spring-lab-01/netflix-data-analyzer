package com.hk.prj.netflix_data_analyzer.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;



public class CognitoLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    public CognitoLogoutHandler(String domain, String logoutRedirectUrl, String userPoolClientId) {
        this.domain = domain;
        this.logoutRedirectUrl = logoutRedirectUrl;
        this.userPoolClientId = userPoolClientId;
    }

    private final String domain;
    private final String logoutRedirectUrl;
    private final String userPoolClientId;

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return UriComponentsBuilder
                .fromUri(URI.create(domain + "/logout"))
                .queryParam("client_id", userPoolClientId)
                .queryParam("logout_uri", logoutRedirectUrl)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}
