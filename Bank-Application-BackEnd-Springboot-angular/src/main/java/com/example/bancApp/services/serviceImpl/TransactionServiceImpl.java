package com.example.bancApp.services.serviceImpl;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.models.Transaction;
import com.example.bancApp.models.TransactionType;
import com.example.bancApp.repositories.TransactionRepository;
import com.example.bancApp.services.EmailService;
import com.example.bancApp.services.TransactionService;
import com.example.bancApp.validator.ObjectValidator;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ObjectValidator validator;
    private final EmailService emailService;
    private final ContactServiceImpl contactService;

    @Transactional
    @Override
    public Integer save(TransactionDto dto)  {
        validator.validate(dto);
        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);
        if(transaction.getType() == TransactionType.TRANSFERT) {
            Transaction transactionSaved = transactionRepository.save(transaction);
            try {
                ContactDto contactDto = contactService.findById(dto.getContactId());

                emailService.sendEmail(dto, contactDto.getId());
            } catch (MessagingException e) {
                System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
                // Tu peux aussi logger proprement ici
            }
            return transactionSaved.getId();
        } else {
            Transaction transactionSaved = transactionRepository.save(transaction);
            return transactionSaved.getId();


        }




    }

    @Override
    public List<TransactionDto> findAll() {

        return transactionRepository.findAll()
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {

        return transactionRepository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("the transaction not found"));
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);

    }

    private int getTransactionMultiplier(TransactionType type) {

        return TransactionType.TRANSFERT == type ? -1 : 1;
    }
    public List<TransactionDto> findAllByUserId(Integer userId){
        return transactionRepository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
