package com.vtm.service;

import com.vtm.dto.request.FleetAssignManagerRequestDto;
import com.vtm.dto.request.FleetAuthRequestDto;
import com.vtm.dto.request.FleetCreateRequestDto;
import com.vtm.dto.request.FleetUpdateRequestDto;
import com.vtm.entity.*;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.repository.IFleetRepository;
import com.vtm.utility.JwtTokenManager;
import com.vtm.utility.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FleetService extends ServiceManager<Fleet, Long> {
    private final IFleetRepository repository;
    private final CompanyService companyService;
    private final RegionService regionService;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    public FleetService(IFleetRepository repository, CompanyService companyService, UserProfileService userProfileService, RegionService regionService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.regionService = regionService;
    }

    public Fleet createfleet(FleetCreateRequestDto dto, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        if (company.equals(null)){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        Region region = regionService.getByRegionId(dto.getRegionId());
        if (region.equals(null)){
            throw new VTMProjectException(EErrorType.REGION_NOT_BE_FOUND);
        }

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
            throw new VTMProjectException(EErrorType.FLEET_NOT_BE_FOUND);
        }
        return fleet.get();
    }

    public Fleet updateByFleetId(FleetUpdateRequestDto dto, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Fleet> fleet = repository.findById(dto.getFleetId());
        if (fleet.isEmpty()){
            throw new VTMProjectException(EErrorType.FLEET_NOT_BE_FOUND);
        }
        fleet.get().setFleetName(dto.getFleetName());
        update(fleet.get());
        return fleet.get();
    }

    public Boolean deleteByFleetId(Long fleetId, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Fleet> fleet = repository.findById(fleetId);
        if (fleet.isEmpty()){
            throw new VTMProjectException(EErrorType.FLEET_NOT_BE_FOUND);
        }
        delete(fleet.get());
        return true;
    }
}
