package com.vtm.controller;

import com.vtm.dto.request.*;

import com.vtm.dto.response.VehicleAuthResponseDto;
import com.vtm.dto.response.VehicleResponseDto;
import com.vtm.dto.response.treeResponse.VehicleTreeDto;
import com.vtm.entity.Fleet;
import com.vtm.entity.Region;
import com.vtm.entity.Vehicle;
import com.vtm.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/createvehicle")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleCreateRequestDto dto, String token){
        return ResponseEntity.ok(vehicleService.createVehicle(dto, token));
    }

    @GetMapping("/getbyvehicleid")
    public ResponseEntity<Vehicle> getByVehicleId(Long vehicleId){
        return ResponseEntity.ok(vehicleService.getByVehicleId(vehicleId));
    }
    @PutMapping("/updatevehicle")
    public ResponseEntity<Vehicle> updateByVehicleId(@RequestBody VehicleUpdateRequestDto dto, String token){
        return ResponseEntity.ok(vehicleService.updateByVehicleId(dto, token));
    }

    @DeleteMapping("/deletebyvehicleid")
    public ResponseEntity<Boolean> deleteByVehicleId(Long vehicleId, String token){
        return ResponseEntity.ok(vehicleService.deleteByVehicleId(vehicleId, token));
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
    public ResponseEntity<VehicleAuthResponseDto> authVehicleByManager(VehicleAuthRequestDto dto, String token){
        return ResponseEntity.ok(vehicleService.authVehicleByManager(dto, token));
    }

    @GetMapping("/vehicletreebyuserid")
    @Operation(summary = "Kullanıcının görevli oldugu bölümleri ve o bölümlerdeki vahicle' ları getiren metot. #1X")
    public ResponseEntity<List<VehicleTreeDto>> vehicleTreeByUserId(Long userId){
        return ResponseEntity.ok(vehicleService.vehicleTreeByUserId(userId));
    }

    @PutMapping("zoneupdate")
    @Operation(summary = "Vehicle' a Region,Fleet, Group ekleyen metot. #1X")
    public ResponseEntity<Boolean> zoneUpdate(ZoneUpdateRequestDto dto, String token){
        return ResponseEntity.ok(vehicleService.zoneUpdate(dto, token));
    }

    @PutMapping("/assignmanagertoregion")
    @Operation(summary = "Region a manager yetkilendiren metot ekleyen metot. #1X")
    public ResponseEntity<Boolean> assignManagerToRegion(AssignUserToRegionRequestDto dto, String token){
        return ResponseEntity.ok(vehicleService.assignManagerToRegion(dto, token));
    }

}
