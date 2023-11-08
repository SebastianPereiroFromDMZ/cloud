package ru.netology.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.netology.entities.User;
import ru.netology.model.SecurityUser;
import ru.netology.security.JwtTokenUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtTokenUtils jwtTokenUtils;
    private final Map<String, String> tokenStore = new HashMap<>();

    public AuthService(JwtTokenUtils jwtTokenUtils) {

        this.jwtTokenUtils = jwtTokenUtils;
    }

    public String loginUser(Authentication authentication) {
        try {
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            String login = user.getUsername();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtils.generateToken(authentication);
            tokenStore.put(token, login);
            return token;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    public void logoutUser(String authToken) {
        tokenStore.remove(authToken);
    }
}