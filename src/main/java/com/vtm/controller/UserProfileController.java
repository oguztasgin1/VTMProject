package com.vtm.controller;

import com.vtm.dto.request.UserUpdateRequestDto;
import com.vtm.entity.UserProfile;
import com.vtm.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping("/getbyuserid")
    @CrossOrigin("*")
    public ResponseEntity<UserProfile> getByUserId(Long userId){
        return ResponseEntity.ok(userProfileService.getByUserId(userId));
    }
    @PutMapping("/updateuser")
    public ResponseEntity<UserProfile> updateByUserId(@RequestBody UserUpdateRequestDto dto, String token){
        return ResponseEntity.ok(userProfileService.updateByUserId(dto,token));
    }

    @DeleteMapping("/deletebyuserid")
    public ResponseEntity<Boolean> deleteByUserId(Long userId, String token){
        return ResponseEntity.ok(userProfileService.deleteByUserId(userId,token));
    }
}
