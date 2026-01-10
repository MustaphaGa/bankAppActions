package com.example.bancApp.services.serviceImpl;

import com.example.bancApp.dto.AddressDto;
import com.example.bancApp.models.Address;
import com.example.bancApp.repositories.AddressRepository;
import com.example.bancApp.services.AddressService;
import com.example.bancApp.validator.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ObjectValidator<AddressDto> validator;
    @Override
    public Integer save(AddressDto dto) {

        validator.validate(dto);
        Address address = AddressDto.toEntity(dto);

        return addressRepository.save(address).getId();
    }

    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(Integer id) {
        return addressRepository.findById(id)
                .map(AddressDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("this address is not existed"));
    }

    @Override
    public void delete(Integer id) {
addressRepository.deleteById(id);
    }
}
