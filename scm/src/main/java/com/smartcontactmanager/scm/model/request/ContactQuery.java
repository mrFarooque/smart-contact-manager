package com.smartcontactmanager.scm.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactQuery {
    List<String> name;
    // add pagination after spring security web filter
}
