package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Mail {
    String mailReceiver;
    String subject;
    String message;
    String toCc;

    public Mail(String mailReceiver, String subject, String message) {
        this.mailReceiver = mailReceiver;
        this.subject = subject;
        this.message = message;
    }

    public void setToCc(String toCc) {
        this.toCc = toCc;
    }
}
