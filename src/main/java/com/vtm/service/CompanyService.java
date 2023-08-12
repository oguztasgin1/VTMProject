package com.vtm.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.vtm.dto.request.CompanyCreateRequestDto;
import com.vtm.dto.request.CompanyUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.Vehicle;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.repository.ICompanyRepository;
import com.vtm.utility.JwtTokenManager;
import com.vtm.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, Long> {
    private final ICompanyRepository repository;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    public CompanyService(ICompanyRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Company createCompany(CompanyCreateRequestDto dto,String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }

        Company company = Company.builder()
                .companyName(dto.getCompanyName())
                .build();
        save(company);
        return company;
    }

    public Company getByCompanyId(Long companyId) {
        Optional<Company> company = repository.findById(companyId);
        if (company.isEmpty()){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        return company.get();
    }

    public Company updateByCompanyId(CompanyUpdateRequestDto dto,String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Company> company = repository.findById(dto.getCompanyId());
        if (company.isEmpty()){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        company.get().setCompanyName(dto.getCompanyName());
        update(company.get());
        return company.get();
    }

    public Boolean deleteByCompanyId(Long companyId,String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Company> company = repository.findById(companyId);
        if (company.isEmpty()){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        delete(company.get());
        return true;
    }
}
