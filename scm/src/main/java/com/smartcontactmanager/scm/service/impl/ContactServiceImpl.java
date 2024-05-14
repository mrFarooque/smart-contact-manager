package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.ContactEntity;
import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.DashBoard;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;
import com.smartcontactmanager.scm.repository.ContactRepository;
import com.smartcontactmanager.scm.service.ContactService;
import com.smartcontactmanager.scm.service.UserService;
import jakarta.persistence.criteria.Subquery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.smartcontactmanager.scm.exception.ErrorCodes.INVALID_IMAGE_CONTENT_TYPE;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public DashBoard dashboard() {
        DashBoard dashBoard = new DashBoard();
        // find userId from the Request Context
        // query database with findAll Contacts with current userId
//        String userId = "constant_user_id";
        String userId = "000";
        long totalContacts = getTotalNumberOfContacts(userId);
        long totalFavouriteContacts = getTotalNumberOfFavouriteContacts(userId);
        dashBoard.setTotalContacts(totalContacts);
        dashBoard.setTotalFavourites(totalFavouriteContacts);
        return dashBoard;
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
    public Contact addContact(String userId, ContactRequest contactRequest) {
        validate(contactRequest);
        UserEntity userEntity = userService.getUserEntityById(userId);
        ContactEntity contactEntity = convertAPIToDAO(contactRequest, userEntity);
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
    public Contacts getContacts(String userId, ContactQuery contactQuery) {
        Specification<ContactEntity> specification = Specification.where(((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userEntity").get("id"), userId)));
        System.out.println("contactQuery: " + contactQuery);
        if (contactQuery != null && contactQuery.getName() != null && !contactQuery.getName().isEmpty()) {
            specification = specification.and(((root, query, criteriaBuilder) -> root.get("name").in(contactQuery.getName())));
        }
        List<ContactEntity> contactEntities = contactRepository.findAll(specification);
        return new Contacts(convertDAOToAPI(contactEntities));
    }

    private void validate(ContactRequest contact) {
        // validate image content type
        String contentType = contact.getImage().getContentType();
        if (contentType != null && !contentType.isEmpty() && !contentType.equals("image/png")) {
            throw new InvalidRequestException(INVALID_IMAGE_CONTENT_TYPE, "Image content type not valid");
        }
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
        contact.setDescription(contactEntity.getDescription());
        contact.setAddress(contactEntity.getAddress());
        contact.setFavourite(contactEntity.getFavorite());
        contact.setEmail(contactEntity.getEmail());
        contact.setImage(contactEntity.getImage());
        return contact;
    }

    private List<Contact> convertDAOToAPI(List<ContactEntity> contactEntities) {
        return contactEntities.stream().map(this::convertDAOToAPI).collect(Collectors.toList());
    }
}
