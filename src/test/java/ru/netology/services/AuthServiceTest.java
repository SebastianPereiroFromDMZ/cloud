package ru.netology.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.netology.dto.AuthRequest;
import ru.netology.security.JwtTokenUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String BAD_USERNAME = "bad_admin";
    private final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
    private final UsernamePasswordAuthenticationToken badAuthenticationToken = new UsernamePasswordAuthenticationToken(BAD_USERNAME, PASSWORD);
    private final String token = UUID.randomUUID().toString();

    private final String badToken = "badToken";
    private final AuthRequest authRequest = new AuthRequest(USERNAME, PASSWORD);

    @Test
    void loginUserTest() {
        Authentication authentication = authenticationManager.authenticate(badAuthenticationToken);
        given(jwtTokenUtils.generateToken(authentication)).willReturn(badToken);
        assertEquals(token, authService.loginUser(authentication));
    }
}