package com.smartcontactmanager.scm.security;

import com.smartcontactmanager.scm.exception.InvalidRequestException;
import com.smartcontactmanager.scm.model.AccessToken;
import com.smartcontactmanager.scm.model.User;
import com.smartcontactmanager.scm.model.enums.Provider;
import com.smartcontactmanager.scm.service.LogInService;
import com.smartcontactmanager.scm.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final String BASE_URL = "http://localhost:5500";
    @Autowired
    private LogInService logInService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        User user = new User();
        user.setName(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setProfilePic(oAuth2User.getAttribute("picture"));
        user.setProvider(Provider.GOOGLE);
        try {
            System.out.println("user creating: " + user.getName());
            logInService.createUser(user);
        } catch (InvalidRequestException e) {
            System.out.println("exception raised");
            if (e.getErrorMessage().equals("user already exists")) {
                System.out.println("user already exists");
                AccessToken accessToken = logInService.generateAccessToken(user.getEmail());
                String redirectUrl = BASE_URL + "?token=" + accessToken.getAccessToken();
                new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
                return;
            }
        }
        AccessToken accessToken = logInService.generateAccessToken(user.getEmail());
        String redirectUrl = BASE_URL + "?token=" + accessToken.getAccessToken();
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
