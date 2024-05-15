package com.smartcontactmanager.scm.service;

import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.model.User;

public interface UserService {
    User createUser(User user);
    UserEntity getUserEntityById(String id);
    User getUserByEmail(String email);
}
