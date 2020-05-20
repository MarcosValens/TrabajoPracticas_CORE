package com.esliceu.core.config;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenFilter()).addPathPatterns("/private/**"); // Este filtro valida el token
    }
}