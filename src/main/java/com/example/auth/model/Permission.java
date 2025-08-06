package com.example.auth.model;

import com.example.auth.constants.Constants;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.apache.bcel.generic.TABLESWITCH;

import java.util.HashSet;
import java.util.Set;

@Entity(name = Constants.PERMISSIONS)
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToMany(mappedBy = "permissions")
//    private Set<Role> roles = new HashSet<>();
}



