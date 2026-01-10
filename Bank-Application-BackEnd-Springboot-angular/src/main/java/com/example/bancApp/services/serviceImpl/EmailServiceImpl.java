package com.example.bancApp.services.serviceImpl;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.models.User;
import com.example.bancApp.services.EmailService;
import com.example.bancApp.services.UserService;
import com.example.bancApp.services.auth.UserDetailsServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;
    private final ContactServiceImpl contactService;

    private final UserDetailsServiceImpl userDetailsService;

    public EmailServiceImpl(JavaMailSender mailSender, UserService userService, ContactServiceImpl contactService, UserDetailsServiceImpl userDetailsService) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.contactService = contactService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void sendEmail(TransactionDto dto, Integer contactId) throws MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderEmail = authentication.getName();


        // Charger les infos complètes via UserService
        User sender = userService.findByEmail(senderEmail);

        // Créer le message pour  l'expéditeur
        MimeMessage senderMessage = mailSender.createMimeMessage();
        MimeMessageHelper senderHelper = new MimeMessageHelper(senderMessage, true);
        senderHelper.setTo(sender.getEmail());
        senderHelper.setSubject("Transaction effectuee");
        senderHelper.setText("Bonjour " + sender.getFirstName() + " " + sender.getLastName() + ",\n\n"
                + "Vous avez fait un " + dto.getType() + " de " + dto.getAmount() + " Dhs  " + ".\n"
                + "Date : " + dto.getTransactionDate() + "\n" + "Merci d’avoir utilisé notre service.", false);

        mailSender.send(senderMessage);

        // Créer le message pour le destinataire
        ContactDto receiver = contactService.findById(contactId);


        MimeMessage receiverMessage = mailSender.createMimeMessage();
        MimeMessageHelper receiverHelper = new MimeMessageHelper(receiverMessage, true);
        receiverHelper.setTo(receiver.getEmail());
        receiverHelper.setSubject("Transaction effectuee");
        receiverHelper.setText("Bonjour " + receiver.getFirstName() + " " + receiver.getLastName() +
                ",\n\n" + "Vous avez recus un montant  de " + dto.getAmount() + " Dhs  " + ".\n"
                + "de la part de " + sender.getFirstName() + " " + sender.getLastName() + "\n" +
                "Date : " + dto.getTransactionDate() + "\n" + "Merci d’avoir utilisé notre service.", false);
        mailSender.send(receiverMessage);


    }
}
