package com.example.bancApp.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OperationNonPermittedExceptions extends RuntimeException {

    private final String errorMessage;
    private final String operationId;
    private final String source;
    private final String dependency;
}
