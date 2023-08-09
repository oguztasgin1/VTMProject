package com.vtm.repository;

import com.vtm.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query("select COUNT(*)>0 from UserProfile a where a.email=?1")
    Boolean isEmail(String email);
    Optional<UserProfile> findOptionalByEmailAndPassword(String email, String password);
}
