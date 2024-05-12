package com.smartcontactmanager.scm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Contacts {
    List<Contact> contacts;
}
