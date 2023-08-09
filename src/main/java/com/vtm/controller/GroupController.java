package com.vtm.controller;


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
    public ResponseEntity<Group> createGroup(GroupCreateRequestDto dto){
        return ResponseEntity.ok(groupService.createGroup(dto));
    }

    @PostMapping("/getbygroupid")
    public ResponseEntity<Group> getByGroupId(Long groupId){
        return ResponseEntity.ok(groupService.getByGroupId(groupId));
    }
    @PutMapping("/updategroup")
    public ResponseEntity<Group> updateByGroupId(@RequestBody GroupUpdateRequestDto dto){
        return ResponseEntity.ok(groupService.updateByGroupId(dto));
    }

    @DeleteMapping("/deletebygroupid")
    public ResponseEntity<Boolean> deleteByGroupId(Long groupId){
        return ResponseEntity.ok(groupService.deleteByGroupId(groupId));
    }
}
