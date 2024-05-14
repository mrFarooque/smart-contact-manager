package com.smartcontactmanager.scm.service;

import com.smartcontactmanager.scm.model.Contact;
import com.smartcontactmanager.scm.model.Contacts;
import com.smartcontactmanager.scm.model.DashBoard;
import com.smartcontactmanager.scm.model.request.ContactQuery;
import com.smartcontactmanager.scm.model.request.ContactRequest;

public interface ContactService {
    DashBoard dashboard();
    Contact addContact(String userId, ContactRequest contact);
    Contact updateContact(String userId, String contactId, Contact contact);
    Contact getContactById(String userId, String contactId);
    Contacts getContacts(String userId, ContactQuery contactQuery);
}
