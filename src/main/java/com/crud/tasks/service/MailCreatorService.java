package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Component
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;


    public String buildTrelloCardEmail(String message){
        List<String> functionality = new ArrayList<>();
        functionality.add("You can message your tasks!");
        functionality.add("Provides connections with Trello Account!");
        functionality.add("Application allows sending tasks to Trello!");
        Context context = new Context();
        context.setVariable("message" , message);
        context.setVariable("tasks_url" , "http://localhost:8888/tasks_frontend/");
        context.setVariable("button" , "Visit website");
        context.setVariable("admin_name" , adminConfig.getAdminName());
        context.setVariable("good_bye_message" , "ALLES GUT FUR DICH");
        context.setVariable("company_name" , adminConfig.getCompanyName());
        context.setVariable("show_button" , false );
        context.setVariable("isFriend" , true);
        context.setVariable("adminConfig" , adminConfig);
        context.setVariable("application_functionality" , functionality);
        return templateEngine.process("mail/created-trello-card-mail" , context);
    }

    public String buildTaskAppEmail(){
        Context context = new Context();
        DbService dbService = new DbService();
        context.setVariable("message" , "Hello there! , you have: " + dbService.taskCounter() + " tasks in ur list taskslist :)");
        context.setVariable("farewell" , "We were glad to see u again :) \n " + adminConfig.getCompanyName());
        context.setVariable("isAMan" , true);
        context.setVariable("motto" , "Our motto is : BLAHBLAHBLAH");
        context.setVariable("tasksList" , dbService.getAllTasks());
        return templateEngine.process("mail/tasks-counter", context);
    }
}
