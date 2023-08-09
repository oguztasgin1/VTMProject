package com.vtm.entity;

import com.vtm.entity.enums.ERole;
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
@Table(name = "tbluserprofile")
public class UserProfile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String password;
    String email;
    String phone;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @OneToMany(mappedBy = "userProfile")
    private Set<Vehicle> vehicles;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
    @OneToMany(mappedBy = "userProfile")
    private Set<Group> groups;
    @OneToMany(mappedBy = "userProfile")
    private Set<Fleet> fleets;
    @OneToMany(mappedBy = "userProfile")
    private Set<Region> regions;
}
