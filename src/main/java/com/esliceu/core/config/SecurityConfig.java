package com.esliceu.core.config;

import com.esliceu.core.filter.AuthenticationSuccess;
import com.esliceu.core.filter.FilterOauth;
import com.esliceu.core.manager.GoogleUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleUserManager googleUserManager;

    @Autowired
    private AuthenticationSuccess authenticationSuccess;

    @Autowired
    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String prefijoUri = environment.getProperty("PREFIJO_URI");

        http

                .cors()
                .and()
                .antMatcher("/oauth2/authorize")
                .addFilterBefore(new FilterOauth(), BasicAuthenticationFilter.class)
                .antMatcher("/**").authorizeRequests()
                .antMatchers(prefijoUri + "/auth/login/flutter", "/auth/recovery", prefijoUri + "/auth/login", prefijoUri + "/oauth2/callback/google", prefijoUri + "/private/**", prefijoUri + "/admin/**", prefijoUri + "/download-zip/**", prefijoUri + "/ldap/*").permitAll()
                .antMatchers(prefijoUri + "/oauth2/authorize").authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri(prefijoUri + "/oauth2/authorize")
                .and()
                .redirectionEndpoint()
                .baseUri(prefijoUri + "/oauth2/callback/google")
                .and()
                .userInfoEndpoint()
                .oidcUserService(googleUserManager)
                .and()
                .successHandler(authenticationSuccess)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(environment.getProperty("cors.allowed").split(",")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "OPTIONS", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
