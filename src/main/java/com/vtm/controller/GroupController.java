package com.vtm.controller;


import com.vtm.dto.request.GroupAssignManagerRequestDto;
import com.vtm.dto.request.GroupAuthRequestDto;
import com.vtm.dto.request.GroupCreateRequestDto;
import com.vtm.dto.request.GroupUpdateRequestDto;
import com.vtm.entity.Group;
import com.vtm.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @PostMapping("/creategroup")
    public ResponseEntity<Group> createGroup(GroupCreateRequestDto dto, String token){
        return ResponseEntity.ok(groupService.createGroup(dto, token));
    }

    @PostMapping("/getbygroupid")
    public ResponseEntity<Group> getByGroupId(Long groupId){
        return ResponseEntity.ok(groupService.getByGroupId(groupId));
    }
    @PutMapping("/updategroup")
    public ResponseEntity<Group> updateByGroupId(@RequestBody GroupUpdateRequestDto dto, String token){
        return ResponseEntity.ok(groupService.updateByGroupId(dto, token));
    }

    @DeleteMapping("/deletebygroupid")
    public ResponseEntity<Boolean> deleteByGroupId(Long groupId, String token){
        return ResponseEntity.ok(groupService.deleteByGroupId(groupId, token));
    }

}
