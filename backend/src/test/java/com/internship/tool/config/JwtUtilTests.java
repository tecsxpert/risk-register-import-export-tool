package com.internship.tool.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTests {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "replace_with_a_long_random_secret_key");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 60000L);
        ReflectionTestUtils.setField(jwtUtil, "refreshExpirationMs", 120000L);
        ReflectionTestUtils.setField(jwtUtil, "issuer", "risk-register-import-export-tool-backend");
    }

    @Test
    void shouldGenerateAndValidateAccessTokenWithPlainTextSecret() {
        UserDetails userDetails = createUserDetails();

        String token = jwtUtil.generateAccessToken(userDetails);

        assertThat(jwtUtil.extractUsername(token)).isEqualTo("anagha@example.com");
        assertThat(jwtUtil.isAccessTokenValid(token, userDetails)).isTrue();
        assertThat(jwtUtil.isRefreshTokenValid(token, userDetails)).isFalse();
    }

    @Test
    void shouldGenerateAndValidateRefreshToken() {
        UserDetails userDetails = createUserDetails();

        String token = jwtUtil.generateRefreshToken(userDetails);

        assertThat(jwtUtil.extractUsername(token)).isEqualTo("anagha@example.com");
        assertThat(jwtUtil.isRefreshTokenValid(token, userDetails)).isTrue();
        assertThat(jwtUtil.isAccessTokenValid(token, userDetails)).isFalse();
    }

    @Test
    void shouldRejectTokenForDifferentUser() {
        UserDetails tokenOwner = createUserDetails();
        UserDetails anotherUser = User.builder()
            .username("other@example.com")
            .password("password")
            .roles("USER")
            .build();

        String token = jwtUtil.generateAccessToken(tokenOwner);

        assertThat(jwtUtil.isAccessTokenValid(token, anotherUser)).isFalse();
    }

    private UserDetails createUserDetails() {
        return User.builder()
            .username("anagha@example.com")
            .password("password")
            .roles("USER")
            .build();
    }
}
