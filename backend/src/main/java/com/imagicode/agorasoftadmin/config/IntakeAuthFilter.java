package com.imagicode.agorasoftadmin.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Filtro sencillo para proteger endpoints de intake con un token compartido.
 * Lee el header X-Admin-Intake-Token o X-INTAKE-TOKEN y lo compara con la
 * propiedad
 * admin.intake.token. Si la propiedad no está configurada, el filtro permite el
 * paso
 * (útil para desarrollo).
 */
@Component
public class IntakeAuthFilter implements Filter {

    @Value("${admin.intake.token:}")
    private String intakeToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Si no hay token configurado en properties/ENV, permitir (dev)
        if (!StringUtils.hasText(intakeToken)) {
            chain.doFilter(request, response);
            return;
        }

        String token = httpRequest.getHeader("X-Admin-Intake-Token");
        if (!StringUtils.hasText(token)) {
            token = httpRequest.getHeader("X-INTAKE-TOKEN");
        }

        if (!StringUtils.hasText(token) || !intakeToken.equals(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid intake token");
            return;
        }

        chain.doFilter(request, response);
    }
}
