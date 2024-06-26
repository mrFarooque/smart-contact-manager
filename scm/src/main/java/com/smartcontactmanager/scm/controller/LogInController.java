package com.smartcontactmanager.scm.controller;

import com.smartcontactmanager.scm.model.AccessToken;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.request.LoginForm;
import com.smartcontactmanager.scm.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LogInController {

    @Autowired
    private LogInService logInService;

    @Autowired
    private WebClient webClient;

    @PostMapping("/tokens")
    public ResponseEntity<AccessToken> generateAccessToken() {
        // generate a jwt token
        return new ResponseEntity<>(logInService.generateAccessToken(), HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> singup(@RequestBody User user) {
        // create user
        logInService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
