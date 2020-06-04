package com.esliceu.core.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterOauth extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        String referer = ((HttpServletRequest) req).getHeader("referer");
        HttpServletResponse response = (HttpServletResponse) res;

        if (referer != null) {
            Cookie cookie = new Cookie("Referer", referer);
            response.addCookie(cookie);
        }

        chain.doFilter(req, response);
    }
}