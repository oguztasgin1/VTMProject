package com.vtm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EErrorType {

    AUTH_PARAMETER_ERROR(1201,"You have entered an invalid parameter",BAD_REQUEST),
    AUTH_PASSWORD_ERROR(1301,"Passwords do not match",BAD_REQUEST),
    AUTH_USERNAME_ERROR(1302,"Username already registered",BAD_REQUEST),

    INTERNAL_ERROR(3000,"Unexpected error on server",INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(4001,"Invalid token information",BAD_REQUEST),
    BAD_REQUEST_ERROR(1202,"You have entered an invalid parameter",BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4113,"Activation code error",HttpStatus.BAD_REQUEST),
    AUTH_NOT_CREATED(4211,"Auth cannot be created",HttpStatus.BAD_REQUEST),

    USER_NOT_BE_FOUND(2301,"The user you were looking for could not be found",INTERNAL_SERVER_ERROR),
    COMPANY_NOT_BE_FOUND(2302,"The company you were looking for could not be found",BAD_REQUEST),
    REGION_NOT_BE_FOUND(2303,"The region you were looking for could not be found",BAD_REQUEST),
    FLEET_NOT_BE_FOUND(2304,"The fleet you were looking for could not be found",BAD_REQUEST),
    GROUP_NOT_BE_FOUND(2305,"The group you were looking for could not be found",BAD_REQUEST),
    VEHICLE_NOT_BE_FOUND(2306,"The vehicle you were looking for could not be found",BAD_REQUEST),

    USER_NOT_IN_COMPANY(2307,"The user you were looking for could not be found that company",BAD_REQUEST);
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
