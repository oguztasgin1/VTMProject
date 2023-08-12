package com.vtm.service;

import com.vtm.dto.request.LoginRequestDto;
import com.vtm.dto.request.RegisterRequestDto;
import com.vtm.dto.request.UserUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.UserProfile;
import com.vtm.entity.enums.ERole;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.repository.IUserProfileRepository;
import com.vtm.utility.JwtTokenManager;
import com.vtm.utility.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;
    private final CompanyService companyService;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    public UserProfileService(IUserProfileRepository repository, CompanyService companyService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
    }

    public boolean isEmail(String email) {
        if (repository.isEmail(email)){
            return true;
        }
        return false;
    }

    public Optional<UserProfile> findOptionalByEmailAndPassword(LoginRequestDto dto) {
        Optional<UserProfile> userProfile =  repository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if (userProfile.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        return userProfile;
    }


    public boolean register(RegisterRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        if (company.equals(null)){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        UserProfile userProfile = UserProfile.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .role(ERole.valueOf(dto.getRole()))
                .company(company)
                .build();
        repository.save(userProfile);
        return true;
    }

    public Boolean deleteByUserId(Long userId, String token) {
        Optional<Long> userId1 = jwtTokenManager.getIdFromToken(token);
        if (userId1.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findById(userId);
        if (userProfile.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        delete(userProfile.get());
        return true;
    }

    public UserProfile updateByUserId(UserUpdateRequestDto dto, String token) {
        Optional<Long> userId1 = jwtTokenManager.getIdFromToken(token);
        if (userId1.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findById(dto.getUserId());
        if (userProfile.isEmpty()) {
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        userProfile.get().setName(dto.getName());
        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setPassword(dto.getPassword());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setSurname(dto.getSurname());
        userProfile.get().setRole(ERole.valueOf(dto.getRole()));
        update(userProfile.get());
        return userProfile.get();
    }

    public UserProfile getByUserId(Long userId) {
        Optional<UserProfile> userProfile = repository.findById(userId);
        if (userProfile.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        return userProfile.get();
    }
}
