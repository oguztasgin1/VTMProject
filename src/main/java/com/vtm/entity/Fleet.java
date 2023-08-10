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
@Table(name = "tblfleet")
public class Fleet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String fleetName;
    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;
    @OneToMany(mappedBy = "fleet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Group> groups;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
