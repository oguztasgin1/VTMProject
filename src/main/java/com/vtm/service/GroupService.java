package com.vtm.service;

import com.vtm.dto.request.GroupAuthRequestDto;
import com.vtm.dto.request.GroupCreateRequestDto;
import com.vtm.dto.request.GroupUpdateRequestDto;
import com.vtm.entity.Company;
import com.vtm.entity.Group;
import com.vtm.entity.UserProfile;
import com.vtm.entity.Vehicle;
import com.vtm.repository.IGroupRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService extends ServiceManager<Group, Long> {
    private final IGroupRepository repository;
    private final CompanyService companyService;
    private final VehicleService vehicleService;
    private final UserProfileService userProfileService;

    public GroupService(IGroupRepository repository, CompanyService companyService, VehicleService vehicleService, UserProfileService userProfileService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.vehicleService = vehicleService;
        this.userProfileService = userProfileService;
    }

    public Group createGroup(GroupCreateRequestDto dto) {
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        Group group = Group.builder()
                .groupName(dto.getGroupName())
                .company(company)
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

    public Boolean addVehicleToGroup(GroupAuthRequestDto dto) {
        Vehicle vehicle = vehicleService.getByVehicleId(dto.getVehicleId());
        if (vehicle.equals(null)){
            System.out.println("Vehicle bulunamadi.");
        }
        Optional<Group> group = repository.findById(dto.getGroupId());
        if (group.isEmpty()){
            System.out.println("Group bulunamadi.");
        }
        group.get().getVehicles().add(vehicle);
        update(group.get());
        UserProfile userProfile = userProfileService.getByUserId(group.get().getUserProfile().getId());
        vehicle.setUserProfile(userProfile);
        vehicleService.update(vehicle);
        return true;
    }
}
