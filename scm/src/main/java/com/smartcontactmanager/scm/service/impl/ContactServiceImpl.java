package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.ContactEntity;
import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.repository.ContactRepository;
import com.smartcontactmanager.scm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.smartcontactmanager.scm.exception.ErrorCodes.INVALID_IMAGE_CONTENT_TYPE;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact addContact(String userId, ContactRequest contact) {
        validate(contact);
        ContactEntity contactEntity = convertAPIToDAO(contact);
        ContactEntity savedContactEntity = contactRepository.save(contactEntity);
        return convertDAOToAPI(savedContactEntity);
    }

    @Override
    public Contact updateContact(String userId, String contactId, Contact contact) {
        return null;
    }

    @Override
    public Contact getContactById(String userId, String contactId) {
        return null;
    }

    @Override
    public Contacts getContacts(String userId) {
        return null;
    }

    private void validate(ContactRequest contact) {
        // validate image content type
        String contentType = contact.getImage().getContentType();
        if (contentType != null && !contentType.isEmpty() && !contentType.equals("image/png")) {
            throw new InvalidRequestException(INVALID_IMAGE_CONTENT_TYPE, "Image content type not valid");
        }
    }

    private ContactEntity convertAPIToDAO(ContactRequest contact) {
        ContactEntity contactEntity = new ContactEntity();
        String contactUUID = UUID.randomUUID().toString();
        contactEntity.setId(contactUUID);
        contactEntity.setName(contact.getName());
        contactEntity.setEmail(contact.getEmail());
        contactEntity.setPhoneNumber(contact.getPhoneNumber());
        contactEntity.setAddress(contact.getAddress());
        contactEntity.setDescription(contact.getDescription());
        contactEntity.setFavorite(contact.getFavourite());
        try {
            contactEntity.setImage(contact.getImage().getBytes());
        } catch (IOException e) {
            contactEntity.setImage(null);
        }
        return contactEntity;
    }

    private Contact convertDAOToAPI(ContactEntity contactEntity) {
        Contact contact = new Contact();
        contact.setId(contactEntity.getId());
        contact.setName(contactEntity.getName());
        contact.setDescription(contactEntity.getDescription());
        contact.setAddress(contactEntity.getAddress());
        contact.setFavourite(contactEntity.getFavorite());
        contact.setEmail(contactEntity.getEmail());
        contact.setImage(contactEntity.getImage());
        return contact;
    }
}
