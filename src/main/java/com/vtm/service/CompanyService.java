package com.vtm.service;

import com.vtm.dto.request.CompanyCreateRequestDto;
import com.vtm.dto.request.CompanyUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.Vehicle;
import com.vtm.repository.ICompanyRepository;
import com.vtm.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, Long> {
    private final ICompanyRepository repository;

    public CompanyService(ICompanyRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Company saveCompany(String companyName) {
        Company company = repository.save(Company.builder().
                companyName(companyName)
                .build());
        return company;
    }

    public Company createCompany(CompanyCreateRequestDto dto) {
        Company company = Company.builder()
                .companyName(dto.getCompanyName())
                .build();
        save(company);
        return company;
    }

    public Company getByCompanyId(Long companyId) {
        Optional<Company> company = repository.findById(companyId);
        if (company.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        return company.get();
    }

    public Company updateByCompanyId(CompanyUpdateRequestDto dto) {
        Optional<Company> company = repository.findById(dto.getCompanyId());
        if (company.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        company.get().setCompanyName(dto.getCompanyName());
        update(company.get());
        return company.get();
    }

    public Boolean deleteByCompanyId(Long companyId) {
        Optional<Company> company = repository.findById(companyId);
        if (company.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        delete(company.get());
        return true;
    }
}
