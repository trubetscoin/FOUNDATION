package com.foundation.security.filter;

import com.foundation.config.AllowedOriginsConfig;
import com.foundation.exceptionHandling.exception.ForbiddenOriginException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Basically CORS filter with extended error message
@Component
public class OriginCheckFilter extends OncePerRequestFilter {

    private static final List<String> ALLOWED_ORIGINS = AllowedOriginsConfig.getAllowedOrigins();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String origin = request.getHeader("Origin");

        if (origin == null || !ALLOWED_ORIGINS.contains(origin)) {
            throw new ForbiddenOriginException(origin);
        }
        filterChain.doFilter(request, response);
    }
}
