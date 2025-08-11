package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.example.auth.model.enums.SelectOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = Constants.PERMISSIONS)
@Getter
@Setter
@NoArgsConstructor
public class Permission  implements SelectOption<UUID> {

    public Permission(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public UUID getValue() {
        return id;
    }

//    @ManyToMany(mappedBy = "permissions")
//    private Set<Role> roles = new HashSet<>();
}



