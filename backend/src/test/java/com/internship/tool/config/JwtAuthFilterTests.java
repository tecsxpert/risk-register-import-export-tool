package com.internship.tool.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTests {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        jwtAuthFilter = new JwtAuthFilter(jwtUtil, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipWhenAuthorizationHeaderIsMissing() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();

        jwtAuthFilter.doFilter(request, new MockHttpServletResponse(), filterChain);

        verify(userDetailsService, never()).loadUserByUsername(org.mockito.ArgumentMatchers.anyString());
        verify(filterChain).doFilter(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void shouldSkipWhenTokenCannotBeParsed() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer bad-token");
        when(jwtUtil.extractUsername("bad-token")).thenThrow(new IllegalArgumentException("Invalid token"));

        jwtAuthFilter.doFilter(request, new MockHttpServletResponse(), filterChain);

        verify(userDetailsService, never()).loadUserByUsername(org.mockito.ArgumentMatchers.anyString());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void shouldAuthenticateWhenAccessTokenIsValid() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        UserDetails userDetails = createUserDetails();

        when(jwtUtil.extractUsername("valid-token")).thenReturn("anagha@example.com");
        when(userDetailsService.loadUserByUsername("anagha@example.com")).thenReturn(userDetails);
        when(jwtUtil.isAccessTokenValid("valid-token", userDetails)).thenReturn(true);

        jwtAuthFilter.doFilter(request, new MockHttpServletResponse(), filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("anagha@example.com");
    }

    @Test
    void shouldNotAuthenticateWhenAccessTokenIsInvalid() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        UserDetails userDetails = createUserDetails();

        when(jwtUtil.extractUsername("invalid-token")).thenReturn("anagha@example.com");
        when(userDetailsService.loadUserByUsername("anagha@example.com")).thenReturn(userDetails);
        when(jwtUtil.isAccessTokenValid("invalid-token", userDetails)).thenReturn(false);

        jwtAuthFilter.doFilter(request, new MockHttpServletResponse(), filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    private UserDetails createUserDetails() {
        return User.builder()
            .username("anagha@example.com")
            .password("password")
            .roles("USER")
            .build();
    }
}
