package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class EmailScheduler {
    private static final String SUBJECT = "Tasks: once a day email";
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;
    private String MESSAGE = "Currently in database u got : ";

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        if (size == 1) {
            emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, MESSAGE + size + " task"));
        } else if (size == 0) {
            emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, MESSAGE + " no tasks"));
        } else
            emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, MESSAGE + size + " tasks"));
    }
}
