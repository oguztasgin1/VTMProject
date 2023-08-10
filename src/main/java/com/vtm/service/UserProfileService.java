package com.vtm.service;

import com.vtm.dto.request.LoginRequestDto;
import com.vtm.dto.request.RegisterRequestDto;
import com.vtm.dto.request.UserUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.UserProfile;
import com.vtm.entity.enums.ERole;
import com.vtm.repository.IUserProfileRepository;
import com.vtm.utility.ServiceManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {
    private final IUserProfileRepository repository;
    private final CompanyService companyService;

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

    public Boolean findOptionalByEmailAndPassword(LoginRequestDto dto) {
        Optional<UserProfile> userProfile =  repository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if (userProfile.isEmpty()){
            return false;
        }
        return true;
    }


    public boolean register(RegisterRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        UserProfile userProfile = UserProfile.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(ERole.valueOf(dto.getRole()))
                .company(company)
                .build();
        repository.save(userProfile);
        return true;
    }

    public Boolean deleteByUserId(Long userId) {
        Optional<UserProfile> userProfile = repository.findById(userId);
        if (userProfile.isEmpty()){
            System.out.println("Kullanici bulunamadi");
            return false;
        }
        delete(userProfile.get());
        return true;
    }

    public UserProfile updateByUserId(UserUpdateRequestDto dto) {
        Optional<UserProfile> userProfile = repository.findById(dto.getUserId());
        if (userProfile.isEmpty()) {
            System.out.println("Kullanıcı bulunamadi");
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
            System.out.println("Kullanici bulunamadi");
        }
        return userProfile.get();
    }
}
