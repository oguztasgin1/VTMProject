package com.vtm.controller;

import com.vtm.dto.request.CompanyCreateRequestDto;
import com.vtm.dto.request.CompanyUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    @PostMapping("/createcompany")
    public ResponseEntity<Company> createCompany(CompanyCreateRequestDto dto){
        return ResponseEntity.ok(companyService.createCompany(dto));
    }

    @PostMapping("/getbycompanyid")
    public ResponseEntity<Company> getByCompanyId(Long companyId){
        return ResponseEntity.ok(companyService.getByCompanyId(companyId));
    }
    @PutMapping("/updatecompany")
    public ResponseEntity<Company> updateByCompanyId(@RequestBody CompanyUpdateRequestDto dto,String token){
        return ResponseEntity.ok(companyService.updateByCompanyId(dto,token));
    }

    @DeleteMapping("/deletebycompanyid")
    public ResponseEntity<Boolean> deleteByCompanyId(Long companyId,String token){
        return ResponseEntity.ok(companyService.deleteByCompanyId(companyId,token));
    }
}
