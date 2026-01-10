package com.example.bancApp.controllers;

import com.example.bancApp.dto.ContactDto;
import com.example.bancApp.services.
        ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Tag(name = "contact")
public class ContactController {

private final ContactService contactService;
    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody ContactDto contactDto
    ){
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactDto>> findAll(){
        return ResponseEntity.ok(contactService.findAll());
    }
    @GetMapping("/{contact-id}")
    public ResponseEntity<ContactDto> findById(
            @PathVariable("contact-id") Integer contactId
    ){ return ResponseEntity.ok(contactService.findById(contactId));

    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<List<ContactDto>> findAllByUserId(
            @PathVariable("user-id") Integer userId
    ){ return ResponseEntity.ok(contactService.findAllByUserId(userId));

    }
    @DeleteMapping("/{contact-id}")
    public ResponseEntity<Integer> delete(
            @PathVariable("contact-id") Integer contactId

    ){ contactService.delete(contactId);
        return ResponseEntity.accepted().build();

    }
}
