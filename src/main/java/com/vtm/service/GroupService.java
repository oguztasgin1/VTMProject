package com.vtm.service;

import com.vtm.dto.request.GroupCreateRequestDto;
import com.vtm.dto.request.GroupUpdateRequestDto;
import com.vtm.entity.Group;
import com.vtm.entity.Vehicle;
import com.vtm.repository.IGroupRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class GroupService extends ServiceManager<Group, Long> {
    private final IGroupRepository repository;
    public GroupService(IGroupRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Group createGroup(GroupCreateRequestDto dto) {
        Group group = Group.builder()
                .groupName(dto.getGroupName())
                .build();
        save(group);
        return group;
    }

    public Group getByGroupId(Long groupId) {
        Optional<Group> group = repository.findById(groupId);
        if (group.isEmpty()){
            System.out.println("Group bulunamadi");
        }
        return group.get();
    }

    public Group updateByGroupId(GroupUpdateRequestDto dto) {
        Optional<Group> group = repository.findById(dto.getGroupId());
        if (group.isEmpty()){
            System.out.println("Group bulunamadi");
        }
        group.get().setGroupName(dto.getGroupName());
        update(group.get());
        return group.get();
    }

    public Boolean deleteByGroupId(Long groupId) {
        Optional<Group> group = repository.findById(groupId);
        if (group.isEmpty()){
            System.out.println("Group bulunamadi");
        }
        delete(group.get());
        return true;
    }
}
