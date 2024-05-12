package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.exception.ResourceNotFoundException;
import com.smartcontactmanager.scm.repository.UserRepository;
import com.smartcontactmanager.scm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.smartcontactmanager.scm.exception.ErrorCodes.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getUserEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND, "user not found", id));
    }
}
