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
    private static final String MESSAGE = "Currently in database u got : ";


    @Scheduled(cron = "0 0 10 * * *")

    public void sendInformationEmail() {
        long size = taskRepository.count();
        String msg = "";
        if (size == 1) {
            msg = MESSAGE + size + " task";
        } else if (size == 0) {
            msg = MESSAGE + " no tasks";
        } else {
            msg = MESSAGE + size + " tasks";

        }
        emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, msg));
    }
}