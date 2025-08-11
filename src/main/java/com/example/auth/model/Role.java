package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.example.auth.model.enums.SelectOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = Constants.ROLES)
@NoArgsConstructor
public class Role implements SelectOption<UUID> {

    public Role(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "ADMIN", "USER"

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public UUID getValue() {
        return id;
    }

//    @OneToMany(mappedBy = "role")  // <-- one role can be assigned to many users
//    private Set<Employee> employees = new HashSet<>();

    // Getters, Setters, equals/hashCode
}
