package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    MailCreatorService mailCreatorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    public void send(Mail mail) {
        LOGGER.info("Starting mail preparation... ");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info(" Mail has been sent");
        } catch (MailException e) {
            LOGGER.error(" MAIL HAS BEEN NOT SENT", e.getMessage());
        }
    }
    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailReceiver());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()) , true);
        };
    }

    private SimpleMailMessage createMessage(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(mail.getMessage());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setTo(mail.getMailReceiver());
        return mailMessage;
    }
}
