package com.vtm.controller;

import com.vtm.dto.request.VehicleAuthRequestDto;
import com.vtm.dto.request.VehicleCreateRequestDto;
import com.vtm.dto.request.VehicleGetAllRequestDto;
import com.vtm.dto.request.VehicleUpdateRequestDto;

import com.vtm.dto.response.VehicleAuthResponseDto;
import com.vtm.dto.response.VehicleResponseDto;
import com.vtm.entity.Vehicle;
import com.vtm.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/createvehicle")
    public ResponseEntity<Vehicle> createVehicle(VehicleCreateRequestDto dto){
        return ResponseEntity.ok(vehicleService.createVehicle(dto));
    }

    @GetMapping("/getbyvehicleid")
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

    @GetMapping("/getallvehiclebycompanyidanduserid")
    @Operation(summary = "Bir Company' deki bir User' a atanmis butun Vehicle' ları CompanyId ve UserId ile getiren metot. #1X")
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicleByCompanyIdAndUserId(VehicleGetAllRequestDto dto){
        return ResponseEntity.ok(vehicleService.getAllVehicleByCompanyIdAndUserId(dto));
    }

    @GetMapping("getallvehiclebycompanyid")
    @Operation(summary = "Bir Company' deki butun Vehicle' ları CompanyId ile getiren metot. #1X")
    public ResponseEntity<List<VehicleResponseDto>> getAllVehiclebyCompanyId(Long companyId){
        return ResponseEntity.ok(vehicleService.getAllVehicleByCompanyId(companyId));
    }

    @PutMapping("/authvehiclebymanager")
    @Operation(summary = "Bir Vehicle' ı bir User a atayan metot. #1X")
    public ResponseEntity<VehicleAuthResponseDto> authVehicleByManager(VehicleAuthRequestDto dto){
        return ResponseEntity.ok(vehicleService.authVehicleByManager(dto));
    }

}
