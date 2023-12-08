package com.pos.exception;

public class CustomException extends RuntimeException {
    private final CustomExceptionEnum exceptionEnum;

    public CustomException(CustomExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public CustomExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}