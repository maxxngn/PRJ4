package com.example.fashion2.dto;

import java.util.List;

public class EmailResponseDTO {
    
    private String emailSubject;
    private String emailBody;
    private String recipient;
    private boolean isSent;
    private List<String> errors;

    public EmailResponseDTO() {
    }

    public EmailResponseDTO(String emailSubject, String emailBody, String recipient, boolean isSent, List<String> errors) {
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.recipient = recipient;
        this.isSent = isSent;
        this.errors = errors;
    }

    // Getters and Setters

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "EmailResponseDTO{" +
                "emailSubject='" + emailSubject + '\'' +
                ", emailBody='" + emailBody + '\'' +
                ", recipient='" + recipient + '\'' +
                ", isSent=" + isSent +
                ", errors=" + errors +
                '}';
    }
}
