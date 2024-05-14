package com.smartcontactmanager.scm.repository;

import com.smartcontactmanager.scm.entity.ContactEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, String> {
    List<ContactEntity> findAll(Specification<ContactEntity> specification);
    long count(Specification<ContactEntity> specification);
}
