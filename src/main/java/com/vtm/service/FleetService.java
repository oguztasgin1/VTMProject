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
    private final VehicleService vehicleService;
    private final UserProfileService userProfileService;
    private final RegionService regionService;

    public FleetService(IFleetRepository repository, CompanyService companyService, VehicleService vehicleService, UserProfileService userProfileService, RegionService regionService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.vehicleService = vehicleService;
        this.userProfileService = userProfileService;
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

    public Boolean addVehicleToFleet(FleetAuthRequestDto dto) {
        Vehicle vehicle = vehicleService.getByVehicleId(dto.getVehicleId());
        if (vehicle.equals(null)){
            System.out.println("Vehicle bulunamadi.");
        }
        Optional<Fleet> fleet = repository.findById(dto.getFleetId());
        if (fleet.isEmpty()){
            System.out.println("Fleet bulunamadi.");
        }
        UserProfile userProfile = userProfileService.getByUserId(fleet.get().getUserProfile().getId());
        vehicle.setUserProfile(userProfile);
        vehicleService.update(vehicle);

        fleet.get().getVehicles().add(vehicle);
        update(fleet.get());

        return true;
    }

    public Boolean assignManagerToFleet(FleetAssignManagerRequestDto dto) {
        UserProfile userProfile = userProfileService.getByUserId(dto.getUserId());
        if (userProfile.equals(null)){
            System.out.println("UserProfile bulunamadi.");
        }
        Optional<Fleet> fleet = repository.findById(dto.getFleetId());
        if (fleet.isEmpty()){
            System.out.println("Fleet bulunamadi.");
        }
        fleet.get().setUserProfile(userProfile);
        update(fleet.get());
        return true;
    }
}
