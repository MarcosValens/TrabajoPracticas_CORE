package com.esliceu.core.config;

import com.esliceu.core.manager.GoogleUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleUserManager googleUserManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers(new String[]{"/"}).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(googleUserManager);
    }
}
