package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/dashboard")
    public void dashboard() {

    }

    @PostMapping("/{userId}/contact")
    public Contact addContact(@PathVariable String userId, ContactRequest contactRequest){
        return contactService.addContact(userId, contactRequest);
    }

    @GetMapping("/{userId}/contact")
    public Contacts getAllContacts(@PathVariable String userId, ContactQuery contactQuery) {
        return contactService.getContacts(userId, contactQuery);
    }

    public void viewAllContacts() {

    }

}

