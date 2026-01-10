package com.example.bancApp.repositories;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Contactrepository extends JpaRepository<Contact,Integer> {

    List<Contact> findAllByUserId(Integer userId);

}
