package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.ContactEntity;
import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.exception.ResourceNotFoundException;
import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.DashBoard;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.repository.ContactRepository;
import com.smartcontactmanager.scm.service.ContactService;
import com.smartcontactmanager.scm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.smartcontactmanager.scm.exception.ErrorCodes.CONTACT_NOT_EXISTS;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public DashBoard dashboard() {
        // find userId from the Request Context
        String userId = getUserIdFromRequestContext();
        // query database with findAll Contacts with current userId
        long totalContacts = getTotalNumberOfContacts(userId);
        long totalFavouriteContacts = getTotalNumberOfFavouriteContacts(userId);
        return new DashBoard(totalContacts, totalFavouriteContacts);
    }

    private String getUserIdFromRequestContext() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    private long getTotalNumberOfFavouriteContacts(String userId) {
        Specification<ContactEntity> specification = Specification.where(((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userEntity").get("id"), userId)));
        specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("favorite"), true)));
        return contactRepository.count(specification);
    }

    private long getTotalNumberOfContacts(String userId) {
        Specification<ContactEntity> specification = Specification.where(((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userEntity").get("id"), userId)));
        return contactRepository.count(specification);
    }

    @Override
    public void addContact(ContactRequest contactRequest) {
        validate(contactRequest);
        String userId = getUserIdFromRequestContext();
        UserEntity userEntity = userService.getUserEntityById(userId);
        ContactEntity contactEntity = convertAPIToDAO(contactRequest, userEntity);
        contactRepository.save(contactEntity);
    }

    @Override
    public void toggleContactToFavourite(String contactId) {
        ContactEntity contactEntity = getContactEntityById(contactId);
        if (contactEntity.getFavorite()) {
            contactEntity.setFavorite(false);
        } else {
            contactEntity.setFavorite(true);
        }
        contactRepository.save(contactEntity);
    }

    @Override
    public void deleteContact(String contactId) {
        ContactEntity contactEntity = getContactEntityById(contactId);
        contactRepository.delete(contactEntity);
    }

    @Override
    public Contact updateContact(String userId, String contactId, Contact contact) {
        return null;
    }

    @Override
    public Contact getContactById(String contactId) {
        return convertDAOToAPI(getContactEntityById(contactId));
    }

    private ContactEntity getContactEntityById(String id) {
        return contactRepository.findByIdAndUserEntityId(id, getUserIdFromRequestContext()).orElseThrow(() -> new ResourceNotFoundException(CONTACT_NOT_EXISTS, "Contact doesn't exists"));
    }

    @Override
    public Contacts getContacts(ContactQuery contactQuery) {
        Specification<ContactEntity> specification = Specification.where(((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userEntity").get("id"), getUserIdFromRequestContext())));
        if (contactQuery != null && contactQuery.getName() != null && !contactQuery.getName().isEmpty()) {
            specification = specification.and(((root, query, criteriaBuilder) -> root.get("name").in(contactQuery.getName())));
        }
        List<ContactEntity> contactEntities = contactRepository.findAll(specification);
        return new Contacts(convertDAOToAPI(contactEntities));
    }

    private void validate(ContactRequest contact) {

    }

    private ContactEntity convertAPIToDAO(ContactRequest contact, UserEntity userEntity) {
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
        contactEntity.setUserEntity(userEntity);
        return contactEntity;
    }

    private Contact convertDAOToAPI(ContactEntity contactEntity) {
        Contact contact = new Contact();
        contact.setId(contactEntity.getId());
        contact.setName(contactEntity.getName());
        contact.setEmail(contactEntity.getEmail());
        contact.setPhoneNumber(contactEntity.getPhoneNumber());
        contact.setDescription(contactEntity.getDescription());
        contact.setAddress(contactEntity.getAddress());
        contact.setImage(contactEntity.getImage());
        contact.setFavourite(contactEntity.getFavorite());
        return contact;
    }

    private List<Contact> convertDAOToAPI(List<ContactEntity> contactEntities) {
        return contactEntities.stream().map(this::convertDAOToAPI).collect(Collectors.toList());
    }
}
