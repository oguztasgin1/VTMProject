package com.vtm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tblregion")
public class Region extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String regionName;
    @OneToMany(mappedBy = "region")
    private Set<Fleet> fleets;
    @OneToMany(mappedBy = "region")
    private Set<Group> groups;
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vehicle> vehicles;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
