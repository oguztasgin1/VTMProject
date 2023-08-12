package com.vtm.controller;


import com.vtm.dto.request.*;
import com.vtm.entity.Fleet;
import com.vtm.service.FleetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fleet")
@RequiredArgsConstructor
public class FleetController {
    private final FleetService fleetService;
    @PostMapping("/createfleet")
    public ResponseEntity<Fleet> createFleet(FleetCreateRequestDto dto){
        return ResponseEntity.ok(fleetService.createfleet(dto));
    }

    @PostMapping("/getbyfleetid")
    public ResponseEntity<Fleet> getByFleetId(Long fleetId){
        return ResponseEntity.ok(fleetService.getByFleetId(fleetId));
    }
    @PutMapping("/updatefleet")
    public ResponseEntity<Fleet> updateByFleetId(@RequestBody FleetUpdateRequestDto dto){
        return ResponseEntity.ok(fleetService.updateByFleetId(dto));
    }

    @DeleteMapping("/deletebyfleetid")
    public ResponseEntity<Boolean> deleteByFleetId(Long fleetId){
        return ResponseEntity.ok(fleetService.deleteByFleetId(fleetId));
    }
}
