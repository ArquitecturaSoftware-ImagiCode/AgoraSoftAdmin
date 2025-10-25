package com.imagicode.agorasoftadmin.servicios;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Renderiza plantillas Thymeleaf para emails.
 * Responsabilidad acotada a "presentación" de correos.
 */
@Component
public class EmailTemplateRenderer {
    private final TemplateEngine templateEngine;

    public EmailTemplateRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /** Render HTML para una plantilla y modelo dado. */
    public String renderHtml(String templateName, Map<String, Object> model) {
        Context ctx = new Context();
        if (model != null)
            model.forEach(ctx::setVariable);
        return templateEngine.process(templateName, ctx);
    }
}