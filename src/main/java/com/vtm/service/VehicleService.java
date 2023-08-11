package com.vtm.service;


import com.vtm.dto.request.*;

import com.vtm.dto.response.VehicleAuthResponseDto;
import com.vtm.dto.response.VehicleResponseDto;
import com.vtm.entity.*;
import com.vtm.repository.IVehicleRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<VehicleResponseDto> getAllVehicleByCompanyIdAndUserId(VehicleGetAllRequestDto dto) {
        List<Vehicle> vehicleList = repository.findAllByCompanyIdAndUserProfileId(dto.getCompanyId(), dto.getUserId());

        return vehicleList.stream().map(x -> VehicleResponseDto.builder()
                .licensePlate(x.getLicensePlate())
                .chassisNumber(x.getChassisNumber())
                .label(x.getLabel())
                .brand(x.getBrand())
                .model(x.getModel())
                .modelYear(x.getModelYear())
                .companyName(x.getCompany().getCompanyName())
                .userName(x.getUserProfile().getName().concat(" ").concat(x.getUserProfile().getSurname()))
//                .fleetName(x.getFleet().getFleetName())
//                .regionName(x.getRegion().getRegionName())
//                .groupName(x.getGroup().getGroupName())
                .build()).collect(Collectors.toList());
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
                .userName(vehicle.get().getUserProfile().getName().concat(" ").concat(vehicle.get().getUserProfile().getSurname()))
                .build();
    }

    public List<VehicleResponseDto> getAllVehicleByCompanyId(Long companyId) {
        List<Vehicle> vehicleList = repository.findAll();
        List<Vehicle> vehicleCompanyList = vehicleList.stream().filter(x-> x.getCompany().getId().equals(companyId)).collect(Collectors.toList());

        return vehicleCompanyList.stream().map(x -> VehicleResponseDto.builder()
                .licensePlate(x.getLicensePlate())
                .chassisNumber(x.getChassisNumber())
                .label(x.getLabel())
                .brand(x.getBrand())
                .model(x.getModel())
                .modelYear(x.getModelYear())
                .companyName(x.getCompany().getCompanyName())
                .userName(x.getUserProfile().getName().concat(" ").concat(x.getUserProfile().getSurname()))
//                .groupName(x.getGroup().getGroupName())
//                .fleetName(x.getFleet().getFleetName())
//                .regionName(x.getRegion().getRegionName())
                .build()).collect(Collectors.toList());
    }

    public Boolean vehicleTreeByUserId(Long userId) {
        UserProfile userProfile = userProfileService.getByUserId(userId);
        List<Vehicle> vehicleList = repository.findAllByCompanyIdAndUserProfileId(userProfile.getCompany().getId(), userId);

        Map<Region, List<Vehicle>> vehicleRegionMap = vehicleList.stream().collect(Collectors.groupingBy(Vehicle::getRegion));

        Map<Region, Map<Fleet, List<Vehicle>>> groupedMap = new HashMap<>();

        for (Map.Entry<Region, List<Vehicle>> entry : vehicleRegionMap.entrySet()) {
            Region region = entry.getKey();
            List<Vehicle> vehicles = entry.getValue();

            Map<Fleet, List<Vehicle>> fleetVehicleMap = vehicles.stream()
                    .collect(Collectors.groupingBy(Vehicle::getFleet));

            groupedMap.put(region, fleetVehicleMap);
        }

        System.out.println(groupedMap);
        printGroupedMap(groupedMap);

        return true;
    }

    private void printGroupedMap(Map<Region, Map<Fleet, List<Vehicle>>> groupedMap) {
        for (Map.Entry<Region, Map<Fleet, List<Vehicle>>> regionEntry : groupedMap.entrySet()) {
            Region region = regionEntry.getKey();
            System.out.println("Region: " + region.getRegionName());

            Map<Fleet, List<Vehicle>> fleetVehicleMap = regionEntry.getValue();

            for (Map.Entry<Fleet, List<Vehicle>> fleetEntry : fleetVehicleMap.entrySet()) {
                Fleet fleet = fleetEntry.getKey();
                System.out.println("  Fleet: " + fleet.getFleetName());

                List<Vehicle> vehicles = fleetEntry.getValue();

                for (Vehicle vehicle : vehicles) {
                    System.out.println("    Vehicle: " + vehicle.getLicensePlate());
                }
            }
        }
    }
}
