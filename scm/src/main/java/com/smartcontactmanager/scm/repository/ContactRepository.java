package com.smartcontactmanager.scm.repository;

import com.smartcontactmanager.scm.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, String> {
}
