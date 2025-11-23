package com.imagicode.agorasoftadmin.servicios;

/**
 * DTO de intención para envío de correos.
 * Evita pasar strings sueltos y hace explícito el contrato del envío.
 *
 * Campos obligatorios: to, from, subject
 * Campos opcionales: htmlBody, textBody
 */
public class EmailMessage {
    private final String to;
    private final String subject;
    private final String from;
    private final String htmlBody;  // opcional
    private final String textBody;  // opcional

    private EmailMessage(Builder b) {
        this.to = b.to;
        this.subject = b.subject;
        this.from = b.from;
        this.htmlBody = b.htmlBody;
        this.textBody = b.textBody;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String to;
        private String subject;
        private String from;
        private String htmlBody;
        private String textBody;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder html(String htmlBody) {
            this.htmlBody = htmlBody;
            return this;
        }

        public Builder text(String textBody) {
            this.textBody = textBody;
            return this;
        }

        public EmailMessage build() {
            // Validaciones básicas
            if (to == null || to.isBlank()) {
                throw new IllegalArgumentException("El campo 'to' es obligatorio.");
            }
            if (from == null || from.isBlank()) {
                throw new IllegalArgumentException("El campo 'from' es obligatorio.");
            }
            if (subject == null || subject.isBlank()) {
                throw new IllegalArgumentException("El campo 'subject' es obligatorio.");
            }
            return new EmailMessage(this);
        }
    }

    @Override
    public String toString() {
        return "EmailMessage{to='" + to + "', from='" + from + "', subject='" + subject + "'}";
    }
}