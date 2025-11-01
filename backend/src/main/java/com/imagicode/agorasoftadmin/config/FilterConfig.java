package com.imagicode.agorasoftadmin.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ClerkAuthFilter> clerkAuthFilterRegistration(ClerkAuthFilter filter) {
        FilterRegistrationBean<ClerkAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/usuario", "/api/protegida/*", "/api/admin/*"); // solo estas rutas requieren
                                                                                         // Clerk
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<IntakeAuthFilter> intakeAuthFilterRegistration(IntakeAuthFilter filter) {
        FilterRegistrationBean<IntakeAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        // proteger los endpoints de intake
        registration.addUrlPatterns("/api/solicitudes", "/api/solicitudes/*");
        registration.setOrder(2);
        return registration;
    }
}
