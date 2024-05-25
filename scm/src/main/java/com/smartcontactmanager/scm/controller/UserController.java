package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.DashBoard;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.service.ContactService;
import com.smartcontactmanager.scm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/dashboard")
    public DashBoard dashboard() {
       return contactService.dashboard();
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

//    @PostMapping("/{userId}/contact")
//    public Contact addContact(@PathVariable String userId, ContactRequest contactRequest){
//        return contactService.addContact(userId, contactRequest);
//    }

//    @GetMapping("/{userId}/contact")
//    public Contacts getAllContacts(@PathVariable String userId, ContactQuery contactQuery) {
//        return contactService.getContacts(contactQuery);
//    }

    public void viewAllContacts() {

    }

}

