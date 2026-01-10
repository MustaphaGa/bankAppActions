package com.example.bancApp.services;

import jakarta.mail.MessagingException;

import java.util.List;

public interface AbstractService<T> {

    Integer save(T dto) ;
    List<T> findAll();
    T findById(Integer id);
    void delete(Integer id);
}
