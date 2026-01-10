package com.example.bancApp.controllers;

import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.services.serviceImpl.EmailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmalController {
    private final EmailServiceImpl emailService;
    public EmalController
            (EmailServiceImpl emailService) {
        this.emailService = emailService;
    }


  /*  @PostMapping("/notify")
    public ResponseEntity<String> notifyTransaction(@RequestBody TransactionDto transactionDto) {
        try {
            emailService.sendEmail( transactionDto, transactionDto.getContactId());
            return ResponseEntity.ok("Emails envoyés avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi des emails.");
        }*/

}
