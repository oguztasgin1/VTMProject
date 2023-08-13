package com.vtm.repository;

import com.vtm.entity.Vehicle;
import com.vtm.entity.View.VehicleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(" SELECT NEW com.vtm.entity.View.VehicleView(v.licensePlate, v.chassisNumber, v.label, v.brand, v.model, v.modelYear) FROM Vehicle v WHERE v.id = :id")
    VehicleView vehicleSpecialQuery(Long id);
    List<Vehicle> findAllByCompanyIdAndUserProfileId(Long companyId, Long userId);

    List<Vehicle> findAllByRegionId(Long regionId);
}
