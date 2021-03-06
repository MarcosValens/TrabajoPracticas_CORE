package com.esliceu.core.config;

import com.esliceu.core.filter.AdminFilter;
import com.esliceu.core.filter.FotoFilter;
import com.esliceu.core.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Bean
    public TokenFilter getTokenFilter() {
        return new TokenFilter();
    }

    @Bean
    public AdminFilter getAdminFilter() {
        return new AdminFilter();
    }

    @Bean
    public FotoFilter getFotoFilter() {return new FotoFilter(); }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenFilter()).addPathPatterns("/private/**", "/admin/**"); // Este filtro valida el token
        registry.addInterceptor(getAdminFilter()).addPathPatterns("/admin/**"); // Este filtro comprueba si el usuario es administrador
        registry.addInterceptor(getFotoFilter()).addPathPatterns("/secret/**");
    }
}