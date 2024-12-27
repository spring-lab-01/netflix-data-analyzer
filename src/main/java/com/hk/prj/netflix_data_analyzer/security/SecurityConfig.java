package com.hk.prj.netflix_data_analyzer.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${oauth.custom.domain}")
    private String domain;
    @Value("${oauth.custom.logoutRedirectUrl:logout}")
    private String logoutRedirectUrl;
    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String userPoolClientId;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CognitoLogoutHandler cognitoLogoutHandler = new CognitoLogoutHandler(domain, logoutRedirectUrl, userPoolClientId);
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/actuator/**").permitAll() // Allow access to the home page
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .oauth2Login(login -> login.defaultSuccessUrl("/" , true))
                .logout(logout -> logout.logoutSuccessHandler(cognitoLogoutHandler))
                .build();
    }
}
