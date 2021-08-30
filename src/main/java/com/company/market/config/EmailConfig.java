package com.company.market.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig {
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Assalomu alaykum Xurmatli: %s.\n" +
                " Registratsiyani tugatish uchun quyidagi linkni bosing: %s");
        return message;
    }
}
