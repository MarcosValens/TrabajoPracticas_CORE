package com.esliceu.core.config;

import com.esliceu.core.filter.AuthenticationSuccess;
import com.esliceu.core.manager.GoogleUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleUserManager googleUserManager;

    @Autowired
    private AuthenticationSuccess authenticationSuccess;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .antMatcher("/**").authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorize")
//                .and()
//                .redirectionEndpoint()
//                .baseUri("/oauth2/callback/google")
//                .and()
//                .userInfoEndpoint()
//                .oidcUserService(googleUserManager)
//                .and()
//                .successHandler(authenticationSuccess);

    }
}
