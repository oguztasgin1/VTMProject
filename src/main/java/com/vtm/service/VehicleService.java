package com.vtm.service;


import com.vtm.dto.request.VehicleCreateRequestDto;
import com.vtm.dto.request.VehicleUpdateRequestDto;

import com.vtm.entity.Vehicle;
import com.vtm.repository.IVehicleRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public class VehicleService extends ServiceManager<Vehicle, Long> {
    private final IVehicleRepository repository;
    public VehicleService(IVehicleRepository repository) {
        super(repository);
        this.repository = repository;
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
        Vehicle vehicle = Vehicle.builder()
                .label(dto.getLabel())
                .licensePlate(dto.getLicensePlate())
                .brand(dto.getBrand())
                .chassisNumber(dto.getChassisNumber())
                .model(dto.getModel())
                .modelYear(dto.getModelYear())
                .build();
        save(vehicle);
        return vehicle;
    }
}
