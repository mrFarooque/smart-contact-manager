package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.exception.ResourceNotFoundException;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.repository.UserRepository;
import com.smartcontactmanager.scm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.smartcontactmanager.scm.exception.ErrorCodes.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        UserEntity userEntity = new UserEntity();
        String userId = UUID.randomUUID().toString();
        userEntity.setId(userId);
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setUserEnabled(user.isUserEnabled());
//        userEntity.setPhoneNumber(user.getPhoneNumber());
//        userEntity.setAbout(user.getAbout());
//        userEntity.setProfilePic(user.getProfilePic());
        return convertDAOToAPI(userRepository.save(userEntity));
    }

    @Override
    public UserEntity getUserEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND, "user not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND, "user not found"));
        return convertDAOToAPI(userEntity);
    }

    private User convertDAOToAPI(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setAbout(userEntity.getAbout());
        user.setProfilePic(userEntity.getProfilePic());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setUserEnabled(userEntity.isUserEnabled());
        user.setPhoneVerified(userEntity.isPhoneVerified());
        user.setEmailVerified(userEntity.isEmailVerified());
        return user;
    }
}
