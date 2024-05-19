package com.smartcontactmanager.scm.service.impl;

import com.smartcontactmanager.scm.entity.UserEntity;
import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.model.AccessToken;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.enums.Provider;
import com.smartcontactmanager.scm.repository.UserRepository;
import com.smartcontactmanager.scm.security.CustomUserDetailsService;
import com.smartcontactmanager.scm.security.helper.JwtUtil;
import com.smartcontactmanager.scm.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.smartcontactmanager.scm.exception.ErrorCodes.USER_ALREADY_EXISTS;

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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email: " + email);
        String token = jwtUtil.generateToken(email);
        return new AccessToken(token);
    }

    @Override
    public AccessToken generateAccessToken(String email) {
        String token = jwtUtil.generateToken(email);
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
        userEntity.setProfilePic(user.getProfilePic());
        if (user.getProvider().equals(Provider.SELF)) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userEntity.setProvider(user.getProvider());
        userEntity.setUserEnabled(user.isUserEnabled());
//        userEntity.setPhoneNumber(user.getPhoneNumber());
//        userEntity.setAbout(user.getAbout());
//        userEntity.setProfilePic(user.getProfilePic());
        convertDAOToAPI(userRepository.save(userEntity));
    }

    private void validateUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new InvalidRequestException(USER_ALREADY_EXISTS, "user already exists");
        }
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
