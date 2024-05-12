package com.smartcontactmanager.scm.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;
    private boolean userEnabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;
}
