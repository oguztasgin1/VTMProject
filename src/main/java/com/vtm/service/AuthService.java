package com.vtm.service;

import com.vtm.dto.request.LoginRequestDto;
import com.vtm.dto.request.RegisterRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserProfileService userService;
    @Transactional
    public Boolean register(RegisterRequestDto dto) {
        if(!dto.getPassword().equals(dto.getPasswordConfirm())){
            System.out.println("throw new AuthException(EErrorType.AUTH_PASSWORD_ERROR);");
        }

        //Mail adresi uniqe olmalı.

        if (userService.register(dto)){
            return true;
        }
        return false;
    }

    public Boolean authenticate(LoginRequestDto dto) {
        if(!userService.findOptionalByEmailAndPassword(dto)){
            System.out.println("Kullanıcı bulunamadi.");
            return false;
        }
        return true;
    }
}
