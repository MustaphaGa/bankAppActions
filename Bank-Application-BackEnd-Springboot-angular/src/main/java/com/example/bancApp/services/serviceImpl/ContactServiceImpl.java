package com.example.bancApp.services.serviceImpl;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.models.Contact;
import com.example.bancApp.repositories.Contactrepository;
import com.example.bancApp.services.ContactService;
import com.example.bancApp.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final Contactrepository contactrepository;
    private final ObjectValidator validator;
    @Override
    public Integer save(ContactDto dto) {
        validator.validate(dto);
        Contact contact = ContactDto.toEntity(dto);
        return contactrepository.save(contact).getId();
    }

    @Override
    public List<ContactDto> findAll() {
        return contactrepository.findAll()
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(Integer id) {
        return contactrepository.findById(id)
                .map(ContactDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("Contact is not found"));
    }

    @Override
    public void delete(Integer id) {
        contactrepository.deleteById(id);

    }

    @Override
    public List<ContactDto> findAllByUserId(Integer userId) {
        return contactrepository.findAllByUserId(userId)
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }


}
