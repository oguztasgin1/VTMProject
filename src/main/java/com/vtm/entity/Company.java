package com.vtm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tblcompany")
public class Company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String companyName;
    @OneToMany(mappedBy = "company")
    Set<UserProfile> userProfileList;
//    @OneToMany(mappedBy = "company")
//    Set<Vehicle> vehicleList;
//    @OneToMany(mappedBy = "company")
//    Set<Group> groups;
//    @OneToMany(mappedBy = "company")
//    Set<Fleet> fleets;
//    @OneToMany(mappedBy = "company")
//    Set<Region> regions;
}
