package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    SimpleEmailService emailService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        // Given
        Mail mail = new Mail("test@test.com", "Test", "Test Message");
//        mail.setToCc("test Cc");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailReceiver());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (mail.getToCc() != null) {
            mailMessage.setCc(mail.getToCc());
        }
        // When
        emailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}