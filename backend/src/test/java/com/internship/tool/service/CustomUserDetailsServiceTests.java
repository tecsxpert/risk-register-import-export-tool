package com.internship.tool.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.internship.tool.entity.UserAccount;
import com.internship.tool.repository.UserAccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTests {

    @Mock
    private UserAccountRepository userAccountRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(userAccountRepository);
    }

    @Test
    void shouldLoadActiveUserByEmail() {
        UserAccount user = createUserAccount(true);
        when(userAccountRepository.findByEmailIgnoreCase("anagha@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(" anagha@example.com ");

        assertThat(userDetails.getUsername()).isEqualTo("anagha@example.com");
        assertThat(userDetails.getPassword()).isEqualTo("encoded-password");
        assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
        assertThat(userDetails.isEnabled()).isTrue();
    }

    @Test
    void shouldMarkInactiveUserAsDisabled() {
        UserAccount user = createUserAccount(false);
        when(userAccountRepository.findByEmailIgnoreCase("anagha@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("anagha@example.com");

        assertThat(userDetails.isEnabled()).isFalse();
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(userAccountRepository.findByEmailIgnoreCase("missing@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("missing@example.com"))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessageContaining("missing@example.com");
    }

    private UserAccount createUserAccount(boolean active) {
        UserAccount user = new UserAccount();
        user.setEmail("anagha@example.com");
        user.setPasswordHash("encoded-password");
        user.setRole("ROLE_ADMIN");
        user.setActive(active);
        return user;
    }
}
