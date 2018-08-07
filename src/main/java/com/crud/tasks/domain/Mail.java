package com.crud.tasks.domain;

import lombok.Getter;

@Getter
public class Mail {
    private String mailReceiver;
    private String subject;
    private String message;
    private String toCc;
    public static final String SUBJECT = "Tasks: New Trello Card";

    public Mail(String mailReceiver, String subject, String message) {
        this.mailReceiver = mailReceiver;
        this.subject = subject;
        this.message = message;
    }

    public void setToCc(String toCc) {
        this.toCc = toCc;
    }

}
