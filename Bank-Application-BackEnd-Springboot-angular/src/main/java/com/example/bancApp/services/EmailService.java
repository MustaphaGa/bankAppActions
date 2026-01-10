package com.example.bancApp.services;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.dto.TransactionDto;
import jakarta.mail.MessagingException;

import java.util.concurrent.locks.Condition;

public interface EmailService {




 void sendEmail(TransactionDto dto, Integer contactId) throws MessagingException;
}
