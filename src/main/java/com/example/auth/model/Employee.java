package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.example.auth.model.enums.SelectOption;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = Constants.EMPLOYEES)
//@ToString(exclude = {"assignedTasks", "managedTasks", "createdTasks", "updatedTasks", "createdProjects", "updatedProjects", "createdComments", "updatedComments"})
public class Employee implements UserDetails, SelectOption<UUID> {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "password", nullable = false)
    private String password;

    // ✅ Self-reference for manager hierarchy
    // ❌NOT USING NOW
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // ✅ Many Employees -> One Role
    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String getLabel() {
        return firstname+" "+lastname;
    }

    @Override
    public UUID getValue() {
        return id;
    }

//    // ✅ Many-to-Many: Employee works on many Projects
//    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
//    private Set<Project> projects;
//
//    // ✅ Tasks assigned to this employee
//    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
//    private Set<Task> assignedTasks;
//
//    // ✅ Tasks managed by this employee
//    @OneToMany(mappedBy = "managedBy", fetch = FetchType.LAZY)
//    private Set<Task> managedTasks;
//
//    // ✅ Tasks created by this employee
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Task> createdTasks;
//
//    // ✅ Tasks updated by this employee
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<Task> updatedTasks;
//
//    // ✅ Comments written by this employee
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Project> createdProjects;
//
//    // ✅ Comments updated by this employee
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<Project> updatedProjects;
//
//    // ✅ Comments created by this employee (audit)
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<CommentController> createdComments;
//
//    // ✅ Comments updated by this employee (audit)
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<CommentController> updatedComments;

    // Getters and Setters

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        role.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Employee employee = (Employee) o;
        return getId() != null && Objects.equals(getId(), employee.getId());
    }

    @Override
    public final int hashCode() {
        return this.getId().hashCode();
    }
}




