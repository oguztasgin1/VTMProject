package com.vtm.controller;


import com.vtm.dto.request.RegionCreateRequestDto;
import com.vtm.dto.request.RegionUpdateRequestDto;
import com.vtm.entity.Region;
import com.vtm.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/createregion")
    public ResponseEntity<Region> createRegion(RegionCreateRequestDto dto){
        return ResponseEntity.ok(regionService.createRegion(dto));
    }

    @PostMapping("/getbyregionid")
    public ResponseEntity<Region> getByRegionId(Long regionId){
        return ResponseEntity.ok(regionService.getByRegionId(regionId));
    }
    @PutMapping("/updateregion")
    public ResponseEntity<Region> updateByRegionId(@RequestBody RegionUpdateRequestDto dto){
        return ResponseEntity.ok(regionService.updateByRegionId(dto));
    }

    @DeleteMapping("/deletebyregionid")
    public ResponseEntity<Boolean> deleteByRegionId(Long regionId){
        return ResponseEntity.ok(regionService.deleteByRegionId(regionId));
    }
}
