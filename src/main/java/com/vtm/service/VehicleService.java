package com.vtm.service;


import com.vtm.dto.request.VehicleAuthRequestDto;
import com.vtm.dto.request.VehicleCreateRequestDto;
import com.vtm.dto.request.VehicleGetAllRequestDto;
import com.vtm.dto.request.VehicleUpdateRequestDto;

import com.vtm.dto.response.VehicleAuthResponseDto;
import com.vtm.dto.response.VehicleResponseDto;
import com.vtm.entity.Company;
import com.vtm.entity.UserProfile;
import com.vtm.entity.Vehicle;
import com.vtm.repository.IVehicleRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService extends ServiceManager<Vehicle, Long> {
    private final IVehicleRepository repository;
    private final CompanyService companyService;
    private final UserProfileService userProfileService;
    public VehicleService(IVehicleRepository repository, CompanyService companyService, UserProfileService userProfileService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.userProfileService = userProfileService;
    }

    public Vehicle getByVehicleId(Long vehicleId) {
        Optional<Vehicle> vehicle = repository.findById(vehicleId);
        if (vehicle.isEmpty()){
            System.out.println("Vehicle bulunamadi");
        }
        return vehicle.get();
    }

    public Vehicle updateByVehicleId(VehicleUpdateRequestDto dto) {
        Optional<Vehicle> vehicle = repository.findById(dto.getVehicleId());
        if (vehicle.isEmpty()) {
            System.out.println("Vehicle bulunamadi");
        }
        vehicle.get().setBrand(dto.getBrand());
        vehicle.get().setChassisNumber(dto.getChassisNumber());
        vehicle.get().setModel(dto.getModel());
        vehicle.get().setModelYear(dto.getModelYear());
        vehicle.get().setLicensePlate(dto.getLicensePlate());
        update(vehicle.get());
        return vehicle.get();
    }

    public Boolean deleteByVehicleId(Long vehicleId) {
        Optional<Vehicle> vehicle = repository.findById(vehicleId);
        if (vehicle.isEmpty()){
            System.out.println("Vehicle bulunamadi");
        }
        delete(vehicle.get());
        return true;
    }

    public Vehicle createVehicle(VehicleCreateRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        UserProfile userProfile = userProfileService.getByUserId(dto.getUserId());

        Vehicle vehicle = Vehicle.builder()
                .label(dto.getLabel())
                .licensePlate(dto.getLicensePlate())
                .brand(dto.getBrand())
                .chassisNumber(dto.getChassisNumber())
                .model(dto.getModel())
                .modelYear(dto.getModelYear())
                .company(company)
                .userProfile(userProfile)
                .build();
        save(vehicle);
        return vehicle;
    }

    public List<VehicleResponseDto> getAllVehicleByCompanyId(VehicleGetAllRequestDto dto) {
        List<Vehicle> vehicleList = repository.findAllByCompanyIdAndUserProfileId(dto.getCompanyId(), dto.getUserId());

        return vehicleList.stream().map(x -> {
            return VehicleResponseDto.builder()
                    .licensePlate(x.getLicensePlate())
                    .chassisNumber(x.getChassisNumber())
                    .label(x.getLabel())
                    .brand(x.getBrand())
                    .model(x.getModel())
                    .modelYear(x.getModelYear())
                    .companyName(x.getCompany().getCompanyName())
                    .userName(x.getUserProfile().getName().concat(" ").concat(x.getUserProfile().getSurname()))
                    .fleetName(x.getFleet().getFleetName())
                    .regionName(x.getRegion().getRegionName())
                    .groupName(x.getGroup().getGroupName())
                    .build();
        }).collect(Collectors.toList());

    }

    public VehicleAuthResponseDto authVehicleByManager(VehicleAuthRequestDto dto) {
        UserProfile userProfile = userProfileService.getByUserId(dto.getUserId());

        if (!(userProfile.getCompany().getId() == dto.getCompanyId())){
            System.out.println("Kullanıcı o bu sikette degil!!");
        }
        Optional<Vehicle> vehicle = repository.findById(dto.getVehicleId());
        if (vehicle.isEmpty()){
            System.out.println("Vehicle bulunamadi.");
        }
        vehicle.get().setUserProfile(userProfile);
        update(vehicle.get());

        return VehicleAuthResponseDto.builder()
                .licensePlate(vehicle.get().getLicensePlate())
                .chassisNumber(vehicle.get().getChassisNumber())
                .label(vehicle.get().getLabel())
                .brand(vehicle.get().getBrand())
                .model(vehicle.get().getModel())
                .modelYear(vehicle.get().getModelYear())
                .companyName(vehicle.get().getCompany().getCompanyName())
                .userName(vehicle.get().getUserProfile().getName().concat("").concat(vehicle.get().getUserProfile().getSurname()))
                .build();
    }
}
