package ru.netology.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import ru.netology.entities.User;
import ru.netology.model.SecurityUser;
import ru.netology.security.JwtTokenUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;
    private final String token = UUID.randomUUID().toString();
    private final SecurityUser user = new SecurityUser(new User());

    @Test
    void loginUserTest() {
        final Authentication authentication = mock(Authentication.class);
        given(jwtTokenUtils.generateToken(authentication)).willReturn(token);
        given((SecurityUser) authentication.getPrincipal()).willReturn(user);

        assertEquals(token, authService.loginUser(authentication));
    }
}