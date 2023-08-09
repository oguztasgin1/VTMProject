package com.vtm.repository;

import com.vtm.entity.Fleet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFleetRepository extends JpaRepository<Fleet, Long> {
}
