package ru.otus.spring.homeworks.ticketpro.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSendingService {

    void sendSimpleEmail(SimpleMailMessage message);
}
