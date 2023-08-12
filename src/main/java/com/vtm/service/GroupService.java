package com.vtm.service;

import com.vtm.dto.request.GroupAssignManagerRequestDto;
import com.vtm.dto.request.GroupAuthRequestDto;
import com.vtm.dto.request.GroupCreateRequestDto;
import com.vtm.dto.request.GroupUpdateRequestDto;
import com.vtm.entity.*;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.repository.IGroupRepository;
import com.vtm.utility.JwtTokenManager;
import com.vtm.utility.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService extends ServiceManager<Group, Long> {
    private final IGroupRepository repository;
    private final CompanyService companyService;

    private final FleetService fleetService;
    private final RegionService regionService;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    public GroupService(IGroupRepository repository, CompanyService companyService, FleetService fleetService, RegionService regionService) {
        super(repository);
        this.repository = repository;
        this.companyService = companyService;
        this.fleetService = fleetService;
        this.regionService = regionService;
    }

    public Group createGroup(GroupCreateRequestDto dto, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Company company = companyService.getByCompanyId(dto.getCompanyId());
        if (company.equals(null)){
            throw new VTMProjectException(EErrorType.COMPANY_NOT_BE_FOUND);
        }
        Fleet fleet = fleetService.getByFleetId(dto.getFleetId());
        if (fleet.equals(null)){
            throw new VTMProjectException(EErrorType.FLEET_NOT_BE_FOUND);
        }
        Long regionId = fleet.getRegion().getId();
        Region region = regionService.getByRegionId(regionId);
        if (region.equals(null)){
            throw new VTMProjectException(EErrorType.REGION_NOT_BE_FOUND);
        }

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
            throw new VTMProjectException(EErrorType.GROUP_NOT_BE_FOUND);
        }
        return group.get();
    }

    public Group updateByGroupId(GroupUpdateRequestDto dto, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Group> group = repository.findById(dto.getGroupId());
        if (group.isEmpty()){
            throw new VTMProjectException(EErrorType.GROUP_NOT_BE_FOUND);
        }
        group.get().setGroupName(dto.getGroupName());
        update(group.get());
        return group.get();
    }

    public Boolean deleteByGroupId(Long groupId, String token) {
        Optional<Long> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty()){
            throw new VTMProjectException(EErrorType.USER_NOT_BE_FOUND);
        }
        Optional<String> role = jwtTokenManager.getRoleFromToken(token);
        if (!(role.get().equals("MANAGER"))){
            throw new VTMProjectException(EErrorType.INVALID_TOKEN);
        }
        Optional<Group> group = repository.findById(groupId);
        if (group.isEmpty()){
            throw new VTMProjectException(EErrorType.GROUP_NOT_BE_FOUND);
        }
        delete(group.get());
        return true;
    }

}
