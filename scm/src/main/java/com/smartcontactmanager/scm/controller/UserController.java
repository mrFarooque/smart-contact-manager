package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.model.Contact;
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

    @PostMapping("/{userId}/addContact")
    public Contact addContact(@PathVariable String userId,
                           @RequestParam String name,
                           @RequestParam(required = false) String email,
                           @RequestParam String phoneNumber,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String description,
                           @RequestParam(required = false) boolean favourite,
                           @RequestParam(required = false) MultipartFile image) throws IOException {
        ContactRequest contactRequest = new ContactRequest(name, email, phoneNumber, address, description, favourite, image);
        return contactService.addContact(userId, contactRequest);
    }

    public void viewAllContacts() {

    }

}

