package com.vtm.service;

import com.vtm.dto.request.GroupAssignManagerRequestDto;
import com.vtm.dto.request.GroupAuthRequestDto;
import com.vtm.dto.request.GroupCreateRequestDto;
import com.vtm.dto.request.GroupUpdateRequestDto;
import com.vtm.entity.*;
import com.vtm.repository.IGroupRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService extends ServiceManager<Group, Long> {
    private final IGroupRepository repository;
    private final CompanyService companyService;
    private final UserProfileService userProfileService;
    private final FleetService fleetService;
    private final RegionService regionService;

    public GroupService(IGroupRepository repository, CompanyService companyService, UserProfileService userProfileService, FleetService fleetService, RegionService regionService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.userProfileService = userProfileService;
        this.fleetService = fleetService;
        this.regionService = regionService;
    }

    public Group createGroup(GroupCreateRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        Fleet fleet = fleetService.getByFleetId(dto.getFleetId());
        Long regionId = fleet.getRegion().getId();
        Region region = regionService.getByRegionId(regionId);

        Group group = Group.builder()
                .groupName(dto.getGroupName())
                .company(company)
                .region(region)
                .fleet(fleet)
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

    public Boolean assignManagerToGroup(GroupAssignManagerRequestDto dto) {
        UserProfile userProfile = userProfileService.getByUserId(dto.getUserId());
        if (userProfile.equals(null)){
            System.out.println("UserProfile bulunamadi.");
        }
        Optional<Group> group = repository.findById(dto.getGroupId());
        if (group.isEmpty()){
            System.out.println("Group bulunamadi.");
        }
        group.get().setUserProfile(userProfile);
        update(group.get());
        return true;
    }
}
