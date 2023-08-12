package com.vtm.service;

import com.vtm.dto.request.LoginRequestDto;
import com.vtm.dto.request.RegisterRequestDto;
import com.vtm.entity.UserProfile;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.utility.JwtTokenManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserProfileService userService;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Transactional
    public Boolean register(RegisterRequestDto dto) {
        if(!dto.getPassword().equals(dto.getPasswordConfirm())){
            throw new VTMProjectException(EErrorType.AUTH_PASSWORD_ERROR);
        }

        if (userService.isEmail(dto.getEmail())){
            throw new VTMProjectException(EErrorType.AUTH_USERNAME_ERROR);
        }

        if (!(userService.register(dto))){
            throw new VTMProjectException(EErrorType.BAD_REQUEST_ERROR);
        }
        return true;
    }

    public String authenticate(LoginRequestDto dto) {
        Optional<UserProfile> userProfile = userService.findOptionalByEmailAndPassword(dto);

        if(userProfile.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> token = jwtTokenManager.createToken
                (userProfile.get().getId(),userProfile.get().getName(),userProfile.get().getSurname(),
                 userProfile.get().getCompany().getId(),userProfile.get().getCompany().getCompanyName(),
                String.valueOf(userProfile.get().getRole()));
        if (token.isEmpty()){
            throw new VTMProjectException(EErrorType.AUTH_NOT_CREATED);
        }
        return token.get();
    }
}
