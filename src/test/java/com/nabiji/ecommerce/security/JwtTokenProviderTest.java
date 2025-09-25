package com.nabiji.ecommerce.security;

import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.enums.Role;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider tokenProvider;

    private User user;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // Manually set the @Value properties for the test environment
        String secret = "mySecretKeyFor4NabijiApplicationThatIsVerySecureAndLongEnough";
        long expiration = 86400000L;
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", secret);
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationMs", expiration);

        // Create a mock user and authentication object
        user = new User();
        user.setUsername("testuser");
        user.setId(1L);
        user.setRole(Role.USER);
        UserPrincipal principal = UserPrincipal.create(user);
        authentication = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
    }

    @Test
    @DisplayName("ტოკენი უნდა დაგენერირდეს წარმატებით")
    void generateToken_shouldCreateValidToken() {
        // Act
        String token = tokenProvider.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("ვალიდური ტოკენი წარმატებით უნდა შემოწმდეს")
    void validateToken_shouldReturnTrue_forValidToken() {
        // Arrange
        String token = tokenProvider.generateToken(authentication);

        // Act
        boolean isValid = tokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("არავალიდური ტოკენი არ უნდა შემოწმდეს")
    void validateToken_shouldReturnFalse_forInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.string";

        // Act
        boolean isValid = tokenProvider.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("ტოკენიდან მომხმარებლის სახელი სწორად უნდა ამოიღოს")
    void getUsernameFromToken_shouldReturnCorrectUsername() {
        // Arrange
        String token = tokenProvider.generateToken(authentication);

        // Act
        String usernameFromToken = tokenProvider.getUsernameFromToken(token);

        // Assert
        assertEquals("testuser", usernameFromToken);
    }
}