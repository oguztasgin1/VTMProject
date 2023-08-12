package com.vtm.exception;

import lombok.Getter;

@Getter
public class VTMProjectException extends RuntimeException{
    private final EErrorType EErrorType;

    public VTMProjectException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }

    public VTMProjectException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }
}
