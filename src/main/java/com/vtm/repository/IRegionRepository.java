package com.vtm.repository;

import com.vtm.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegionRepository extends JpaRepository<Region, Long> {
}
