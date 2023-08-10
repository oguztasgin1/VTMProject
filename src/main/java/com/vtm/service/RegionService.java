package com.vtm.service;

import com.vtm.dto.request.RegionCreateRequestDto;
import com.vtm.dto.request.RegionUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.Region;

import com.vtm.repository.IRegionRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionService extends ServiceManager<Region, Long> {
    private final IRegionRepository repository;
    private final CompanyService companyService;

    public RegionService(IRegionRepository repository, CompanyService companyService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
    }

    public Region createRegion(RegionCreateRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        Region region = Region.builder()
                .regionName(dto.getRegionName())
                .company(company)
                .build();
        save(region);
        return region;
    }

    public Region getByRegionId(Long regionId) {
        Optional<Region> region = repository.findById(regionId);
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        return region.get();
    }

    public Region updateByRegionId(RegionUpdateRequestDto dto) {
        Optional<Region> region = repository.findById(dto.getRegionId());
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        region.get().setRegionName(dto.getRegionName());
        update(region.get());
        return region.get();
    }

    public Boolean deleteByRegionId(Long regionId) {
        Optional<Region> region = repository.findById(regionId);
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        delete(region.get());
        return true;
    }
}
