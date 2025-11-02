package com.imagicode.agorasoftadmin.servicios;

/**
 * DTO que representa el mensaje de correo que se envía.
 * Evita pasar strings "sueltos" y clarifica contrato entre render y envío.
 */
public class EmailMessage {
    private final String to;
    private final String from;
    private final String subject;
    private final String htmlBody;
    private final String textBody;

    private EmailMessage(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.subject = b.subject;
        this.htmlBody = b.htmlBody;
        this.textBody = b.textBody;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
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
        private String from;
        private String subject;
        private String htmlBody;
        private String textBody;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
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
            return new EmailMessage(this);
        }
    }
}