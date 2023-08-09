package com.vtm.controller;

import com.vtm.dto.request.VehicleCreateRequestDto;
import com.vtm.dto.request.VehicleUpdateRequestDto;

import com.vtm.entity.Vehicle;
import com.vtm.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/createvehicle")
    public ResponseEntity<Vehicle> createVehicle(VehicleCreateRequestDto dto){
        return ResponseEntity.ok(vehicleService.createVehicle(dto));
    }

    @PostMapping("/getbyvehicleid")
    public ResponseEntity<Vehicle> getByVehicleId(Long vehicleId){
        return ResponseEntity.ok(vehicleService.getByVehicleId(vehicleId));
    }
    @PutMapping("/updatevehicle")
    public ResponseEntity<Vehicle> updateByVehicleId(@RequestBody VehicleUpdateRequestDto dto){
        return ResponseEntity.ok(vehicleService.updateByVehicleId(dto));
    }

    @DeleteMapping("/deletebyvehicleid")
    public ResponseEntity<Boolean> deleteByVehicleId(Long vehicleId){
        return ResponseEntity.ok(vehicleService.deleteByVehicleId(vehicleId));
    }

}
