package com.vtm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tblvehicle")
public class Vehicle extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long licensePlate;
    Long chassisNumber;
    String label;
    String brand;
    String model;
    String modelYear;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    UserProfile userProfile;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @ManyToOne
    @JoinColumn(name = "fleet_id")
    private Fleet fleet;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
