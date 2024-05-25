package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestParam("name") String name,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "address", required = false) String address,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           @RequestParam(value = "favourite", required = false, defaultValue = "false") Boolean favourite) {
        ContactRequest contactRequest = new ContactRequest(name, email, phone, address, description, image, favourite);
        contactService.addContact(contactRequest);
    }

    @PutMapping("/{id}/favourite")
    @ResponseStatus(HttpStatus.OK)
    public void toggleContactToFavourite(@PathVariable("id") String contactId) {
        contactService.toggleContactToFavourite(contactId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contact getContactById(@PathVariable("id") String contactId) {
        return contactService.getContactById(contactId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Contacts getContacts(ContactQuery contactQuery) {
        return contactService.getContacts(contactQuery);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") String contactId) {
        contactService.deleteContact(contactId);
    }
}
