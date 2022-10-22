package com.cf.EventApp.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @EqualsAndHashCode
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username must not be blank")
    @Size(max = 75, message = "Username must be less than 75 characters")
    @Column(name = "username")
    private String username;

    @Size(max = 75, message = "Email must be less than 75 characters")
    @Column(name = "email")
    private String email;

    @Size(max = 100, message = "Password must be less than 100 characters")
    @Column(name = "password")
    private String password;

    @Size(max = 25, message = "First name must be less than 25 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50, message = "Last name must be less than 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "snap_chat")
    private String snapChat;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private Provider provider;      // Facebook and Google login
}
