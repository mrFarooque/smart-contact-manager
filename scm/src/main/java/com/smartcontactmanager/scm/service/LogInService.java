package com.smartcontactmanager.scm.service;

import com.smartcontactmanager.scm.model.AccessToken;
import com.smartcontactmanager.scm.model.User;

public interface LogInService {
    AccessToken generateAccessToken();
    AccessToken generateAccessToken(String email);
    void createUser(User user);
}
