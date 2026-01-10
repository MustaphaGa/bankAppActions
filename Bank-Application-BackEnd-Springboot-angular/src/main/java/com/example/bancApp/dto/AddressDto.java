package com.example.bancApp.dto;

import com.example.bancApp.models.Address;
import com.example.bancApp.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddressDto {

    private Integer id;
    private  String street;
    private  String county;
    private  String city;
    private  Integer zipCode;
    private  Integer houseNumber;

    private Integer userId;

    public static  AddressDto fromEntity(Address  address){
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .county(address.getCounty())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .houseNumber(address.getHouseNumber())
                .userId(address.getUser().getId())
                .build();
    }
    public static Address toEntity(AddressDto addressDto){
        return Address.builder()
                .id(addressDto.getId())
                .street(addressDto.getStreet())
                .county(addressDto.getCounty())
                .city(addressDto.getCity())
                .zipCode(addressDto.getZipCode())
                .houseNumber(addressDto.getHouseNumber())
                .user(
                        User.builder()
                                .id(addressDto.getUserId())
                                .build()
                )
                .build();
    }

}
