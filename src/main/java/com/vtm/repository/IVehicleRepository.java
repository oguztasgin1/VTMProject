package com.vtm.repository;

import com.vtm.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByCompanyIdAndUserProfileId(Long companyId, Long userId);

    List<Vehicle> findAllByRegionId(Long regionId);
}
