package com.smartcontactmanager.scm.entity;

import com.smartcontactmanager.scm.model.enums.Provider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "about")
    private String about;
    @Column(name = "profile_pic")
    private String profilePic;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "user_enabled")
    private boolean userEnabled = false;
    @Column(name = "email_verified")
    private boolean emailVerified = false;
    @Column(name = "phone_verified")
    private boolean phoneVerified = false;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;
    @Column(name = "provider_user_id")
    private String providerUserId;

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ContactEntity> contactEntities = new ArrayList<>();

}
