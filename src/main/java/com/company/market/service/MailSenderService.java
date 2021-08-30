package com.company.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String toAccount, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount); // example@gmail.com
        msg.setSubject("Digital Market verification"); // Subject of mail
        msg.setText(text); // Content of mail
        javaMailSender.send(msg);
    }
    //        System.out.println(update);


}

