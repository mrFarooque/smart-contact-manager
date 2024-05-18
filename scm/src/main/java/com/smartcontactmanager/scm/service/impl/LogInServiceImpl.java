package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.model.AccessToken;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.repository.UserRepository;
import com.smartcontactmanager.scm.security.CustomUserDetailsService;
import com.smartcontactmanager.scm.security.helper.JwtUtil;
import com.smartcontactmanager.scm.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogInServiceImpl implements LogInService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AccessToken generateAccessToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String token = jwtUtil.generateToken(username);
        return new AccessToken(token);
    }

    @Override
    public void createUser(User user) {
        validateUser(user);
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
        convertDAOToAPI(userRepository.save(userEntity));
    }

    private void validateUser(User user) {

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
