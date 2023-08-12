package com.vtm.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtm.dto.request.*;

import com.vtm.dto.response.VehicleAuthResponseDto;
import com.vtm.dto.response.VehicleResponseDto;
import com.vtm.dto.response.treeResponse.FleetDto;
import com.vtm.dto.response.treeResponse.GroupDto;
import com.vtm.dto.response.treeResponse.VehicleTreeDto;
import com.vtm.entity.*;
import com.vtm.repository.IVehicleRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService extends ServiceManager<Vehicle, Long> {
    private final IVehicleRepository repository;
    private final CompanyService companyService;
    private final UserProfileService userProfileService;
    private final RegionService regionService;
    private final FleetService fleetService;
    private final GroupService groupService;
    public VehicleService(IVehicleRepository repository, CompanyService companyService, UserProfileService userProfileService, RegionService regionService, FleetService fleetService, GroupService groupService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.userProfileService = userProfileService;
        this.regionService = regionService;
        this.fleetService = fleetService;
        this.groupService = groupService;
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
                .fleetName(x.getFleet().getFleetName())
                .regionName(x.getRegion().getRegionName())
                .groupName(x.getGroup().getGroupName())
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
                .groupName(x.getGroup().getGroupName())
                .fleetName(x.getFleet().getFleetName())
                .regionName(x.getRegion().getRegionName())
                .build()).collect(Collectors.toList());
    }

    public List<VehicleTreeDto> vehicleTreeByUserId(Long userId) {
        UserProfile userProfile = userProfileService.getByUserId(userId);
        List<Vehicle> vehicleList = repository.findAllByCompanyIdAndUserProfileId(userProfile.getCompany().getId(), userId);

        Map<Region, List<Vehicle>> vehicleRegionMap = vehicleList.stream().collect(Collectors.groupingBy(Vehicle::getRegion));

        Map<Region, Map<Fleet, Map<Group, List<Vehicle>>>> groupedMap = new HashMap<>();

        for (Map.Entry<Region, List<Vehicle>> regionEntry : vehicleRegionMap.entrySet()) {
            Region region = regionEntry.getKey();
            List<Vehicle> vehicles = regionEntry.getValue();

            Map<Fleet, Map<Group, List<Vehicle>>> fleetGroupVehicleMap = new HashMap<>();
            for (Vehicle vehicle : vehicles) {
                Fleet fleet = vehicle.getFleet();
                Group group = vehicle.getGroup();

                fleetGroupVehicleMap.computeIfAbsent(fleet, k -> new HashMap<>())
                        .computeIfAbsent(group, k -> new ArrayList<>())
                        .add(vehicle);
            }

            groupedMap.put(region, fleetGroupVehicleMap);
        }

        printGroupedMap(groupedMap);


        return convertToDto(groupedMap);
    }

    public List<VehicleTreeDto> convertToDto(Map<Region, Map<Fleet, Map<Group, List<Vehicle>>>> groupedMap) {
        List<VehicleTreeDto> vehicleTreeDtos = new ArrayList<>();

        for (Map.Entry<Region, Map<Fleet, Map<Group, List<Vehicle>>>> regionEntry : groupedMap.entrySet()) {
            Region region = regionEntry.getKey();
            VehicleTreeDto regionDto = new VehicleTreeDto();
            regionDto.setRegionName(region.getRegionName());

            List<FleetDto> fleetDtos = new ArrayList<>();
            Map<Fleet, Map<Group, List<Vehicle>>> fleetGroupVehicleMap = regionEntry.getValue();

            for (Map.Entry<Fleet, Map<Group, List<Vehicle>>> fleetEntry : fleetGroupVehicleMap.entrySet()) {
                Fleet fleet = fleetEntry.getKey();
                FleetDto fleetDto = new FleetDto();
                fleetDto.setFleetName(fleet.getFleetName());

                List<GroupDto> groupDtos = new ArrayList<>();
                Map<Group, List<Vehicle>> groupVehicleMap = fleetEntry.getValue();

                for (Map.Entry<Group, List<Vehicle>> groupEntry : groupVehicleMap.entrySet()) {
                    Group group = groupEntry.getKey();
                    GroupDto groupDto = new GroupDto();
                    groupDto.setGroupName(group.getGroupName());

                    List<String> vehicleLicensePlates = groupEntry.getValue().stream()
                            .map(Vehicle::getLicensePlate)
                            .collect(Collectors.toList());

                    groupDto.setVehicles(vehicleLicensePlates);
                    groupDtos.add(groupDto);
                }

                fleetDto.setGroups(groupDtos);
                fleetDtos.add(fleetDto);
            }

            regionDto.setFleets(fleetDtos);
            vehicleTreeDtos.add(regionDto);
        }

        return vehicleTreeDtos;
    }

    private void printGroupedMap(Map<Region, Map<Fleet, Map<Group, List<Vehicle>>>> groupedMap) {
        for (Map.Entry<Region, Map<Fleet, Map<Group, List<Vehicle>>>> regionEntry : groupedMap.entrySet()) {
            Region region = regionEntry.getKey();
            System.out.println("Region: " + region.getRegionName());

            Map<Fleet, Map<Group, List<Vehicle>>> fleetGroupVehicleMap = regionEntry.getValue();

            for (Map.Entry<Fleet, Map<Group, List<Vehicle>>> fleetEntry : fleetGroupVehicleMap.entrySet()) {
                Fleet fleet = fleetEntry.getKey();
                System.out.println("  Fleet: " + fleet.getFleetName());

                Map<Group, List<Vehicle>> groupVehicleMap = fleetEntry.getValue();

                for (Map.Entry<Group, List<Vehicle>> groupEntry : groupVehicleMap.entrySet()) {
                    Group group = groupEntry.getKey();
                    System.out.println("    Group: " + group.getGroupName());

                    List<Vehicle> vehicles = groupEntry.getValue();

                    for (Vehicle vehicle : vehicles) {
                        System.out.println("      Vehicle: " + vehicle.getLicensePlate());
                    }
                }
            }
        }
    }

    public Boolean zoneUpdate(ZoneUpdateRequestDto dto) {
        Optional<Vehicle> vehicle = repository.findById(dto.getVehicleId());
        if (vehicle.isEmpty()){
            System.out.println("Vehicle bulunamadi.");
        }

        Region region = regionService.getByRegionId(dto.getRegionId());

        Fleet fleet = fleetService.getByFleetId(dto.getFleetId());

        Group group = groupService.getByGroupId(dto.getGroupId());

        vehicle.get().setRegion(region);
        vehicle.get().setFleet(fleet);
        vehicle.get().setGroup(group);
        update(vehicle.get());
        return true;
    }

    public Boolean assignManagerToRegion(AssignUserToRegionRequestDto dto) {
        List<Vehicle> vehicles = repository.findAllByRegionId(dto.getRegionId());
        UserProfile userProfile = userProfileService.getByUserId(dto.getUserId());

        for (Vehicle vehicle: vehicles) {
            vehicle.setUserProfile(userProfile);
        }
        saveAll(vehicles);

        return true;
    }
}
