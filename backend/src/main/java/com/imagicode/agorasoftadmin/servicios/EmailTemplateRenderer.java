package com.imagicode.agorasoftadmin.servicios;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.TemplateEngine;

/**
 * Servicio responsable de renderizar plantillas Thymeleaf para correos.
 */
@Service
public class EmailTemplateRenderer {

    private final SpringTemplateEngine templateEngine;

    public EmailTemplateRenderer(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Renderiza plantilla Thymeleaf sin sufijo. Ej: "email/user-registered"
     */
    public String renderHtml(String templateName, Map<String, Object> model) {
        Context ctx = new Context();
        if (model != null)
            model.forEach(ctx::setVariable);
        return templateEngine.process(templateName, ctx);
    }
}