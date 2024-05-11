package com.smartcontactmanager.scm.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private Boolean favourite;
    private byte[] image;
}
