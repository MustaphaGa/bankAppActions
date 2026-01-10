package com.example.bancApp.controllers;

import com.example.bancApp.dto.AddressDto;
import com.example.bancApp.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.security.DrbgParameters;
import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@Tag(name = "address")

public class AddressController {
    private final AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody AddressDto addressDto
    ){
        return ResponseEntity.ok(addressService.save(addressDto));
    }

    @GetMapping("/{address-id}")
    public ResponseEntity<AddressDto> findById(
            @PathVariable("address-id") Integer addressId
    ){ return ResponseEntity.ok(addressService.findById(addressId));

    }
    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @DeleteMapping("/{address-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("address-id") Integer addressId
    ){
        addressService.delete(addressId);
        return ResponseEntity.accepted().build();

    }
}
