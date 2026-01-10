package com.example.bancApp.handlers;

import com.example.bancApp.exceptions.ObjectValidationException;
import com.example.bancApp.exceptions.OperationNonPermittedExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<ExceptionRepresentation> handelException(ObjectValidationException exception){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Object not valid has occured")
                .validationErrors(exception.getViolations())
                .errorSource(exception.getViolationSource())
                .build();
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

    @ExceptionHandler(OperationNonPermittedExceptions.class)
    public ResponseEntity<ExceptionRepresentation> handelException(OperationNonPermittedExceptions exception){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getErrorMessage())
                .build();
        return  ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);


    }
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ExceptionRepresentation> handelException(){
    ExceptionRepresentation representation = ExceptionRepresentation.builder()
            .errorMessage("A user already existed with the same email")
            .build();
    return  ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(representation);


}


    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionRepresentation> handelDisableException(){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("you can not access your account is not yet activated")
                .build();
        return  ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);


    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRepresentation> handelBadCredentialsException(){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("your email and or password is incorrect")
                .build();
        return  ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);


    }




}
