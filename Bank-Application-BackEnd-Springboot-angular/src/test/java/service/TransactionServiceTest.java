package service;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.dto.TransactionDto;
import com.example.bancApp.models.Transaction;
import com.example.bancApp.models.TransactionType;
import com.example.bancApp.models.User;
import com.example.bancApp.repositories.TransactionRepository;
import com.example.bancApp.services.serviceImpl.ContactServiceImpl;
import com.example.bancApp.services.serviceImpl.EmailServiceImpl;
import com.example.bancApp.services.serviceImpl.TransactionServiceImpl;
import com.example.bancApp.validator.ObjectValidator;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    ContactServiceImpl contactService;

    @Mock
    EmailServiceImpl emailService;
    @Mock
    ObjectValidator validator;
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void findTransactionByIdSuccess() {
        User user = new User();
        user.setId(10);
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setUser(user);

        Mockito.when(transactionRepository.findById(1))
                .thenReturn(Optional.of(transaction));
        TransactionDto foundTransaction = transactionService.findById(1);

        assertNotNull(foundTransaction);
        assertEquals(1, foundTransaction.getId());
        assertEquals(10, foundTransaction.getUserId()); // si ton DTO contient userId
        Mockito.verify(transactionRepository, Mockito.times(1)).findById(1);

    }

    @Test
    void findTransactionByIdNotFound() {
        Mockito.when(transactionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            transactionService.findById(1);

        });
    }

    @Test
    void saveTransactionSuccesfully() throws MessagingException {
        User user = new User();
        user.setId(10);

        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAmount(BigDecimal.valueOf(1200));
        transaction.setDestinationIban("IBAN");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setType(TransactionType.TRANSFERT);
        transaction.setUser(user);

        TransactionDto dto = TransactionDto.fromEntity(transaction);
        dto.setContactId(5);

        ContactDto contactDto = new ContactDto();
        contactDto.setId(5);

        Mockito.doNothing().when(validator).validate(dto);

        Mockito.when(contactService.findById(5)).thenReturn(contactDto);

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenAnswer(invocation -> {
                    Transaction t = invocation.getArgument(0);
                    t.setId(1);
                    return t;
                });
        Integer transactionSaved = transactionService.save(dto);


        Mockito.verify(transactionRepository, Mockito.times(1))
                .save(Mockito.any(Transaction.class));

        Mockito.verify(contactService, Mockito.times(1)).findById(5);

        Mockito.verify(emailService, Mockito.times(1))
                .sendEmail(dto, 5);

        assertEquals(1, transactionSaved);
        assertEquals(10, dto.getUserId()); // si ton DTO contient userId
        assertEquals(5, dto.getContactId());


    }

    @Test
    void findAllTransactionsSuccesfully() {
        User user = new User();
        user.setId(10);

        Transaction t1 = new Transaction();
        t1.setId(1);
        t1.setAmount(BigDecimal.valueOf(100));
        t1.setUser(user);

        Transaction t2 = new Transaction();
        t2.setId(2);
        t2.setAmount(BigDecimal.valueOf(100));
        t2.setUser(user);

        List<Transaction> transactions = List.of(t1, t2);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        List<TransactionDto> foundTransactions = transactionService.findAll();

        assertNotNull(foundTransactions);
        assertEquals(2, foundTransactions.size());
        assertEquals(1, foundTransactions.get(0).getId());
        assertEquals(2, foundTransactions.get(1).getId());

        Mockito.verify(transactionRepository, Mockito.times(1)).findAll();


    }
}
