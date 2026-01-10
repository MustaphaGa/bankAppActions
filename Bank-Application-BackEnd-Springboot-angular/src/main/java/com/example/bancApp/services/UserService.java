package com.example.bancApp.services;

import com.example.bancApp.dto.AuthenticationRequest;
import com.example.bancApp.dto.AuthenticationResponse;
import com.example.bancApp.dto.UserDto;
import com.example.bancApp.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends AbstractService<UserDto> {

    User findByEmail(String email) throws UsernameNotFoundException;

    Integer validateAccount(Integer id);
    Integer  invalidateAcount(Integer id);

   AuthenticationResponse register(UserDto userDto);

    AuthenticationResponse aunthenticate(AuthenticationRequest request);
}
