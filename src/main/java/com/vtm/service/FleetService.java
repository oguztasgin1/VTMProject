package com.vtm.service;

import com.vtm.dto.request.FleetAssignManagerRequestDto;
import com.vtm.dto.request.FleetAuthRequestDto;
import com.vtm.dto.request.FleetCreateRequestDto;
import com.vtm.dto.request.FleetUpdateRequestDto;
import com.vtm.entity.*;
import com.vtm.repository.IFleetRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FleetService extends ServiceManager<Fleet, Long> {
    private final IFleetRepository repository;
    private final CompanyService companyService;
    private final RegionService regionService;

    public FleetService(IFleetRepository repository, CompanyService companyService, UserProfileService userProfileService, RegionService regionService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.regionService = regionService;
    }

    public Fleet createfleet(FleetCreateRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        Region region = regionService.getByRegionId(dto.getRegionId());

        Fleet fleet = Fleet.builder()
                .fleetName(dto.getFleetName())
                .company(company)
                .region(region)
                .build();
        save(fleet);
        return fleet;
    }

    public Fleet getByFleetId(Long fleetId) {
        Optional<Fleet> fleet = repository.findById(fleetId);
        if (fleet.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        return fleet.get();
    }

    public Fleet updateByFleetId(FleetUpdateRequestDto dto) {
        Optional<Fleet> fleet = repository.findById(dto.getFleetId());
        if (fleet.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        fleet.get().setFleetName(dto.getFleetName());
        update(fleet.get());
        return fleet.get();
    }

    public Boolean deleteByFleetId(Long fleetId) {
        Optional<Fleet> fleet = repository.findById(fleetId);
        if (fleet.isEmpty()){
            System.out.println("Company bulunamadi");
        }
        delete(fleet.get());
        return true;
    }
}
