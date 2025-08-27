package com.omeshwar.project.airBNB.entity;


import com.omeshwar.project.airBNB.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


}
