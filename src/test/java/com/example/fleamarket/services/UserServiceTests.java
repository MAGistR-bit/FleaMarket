package com.example.fleamarket.services;

import com.example.fleamarket.models.User;
import com.example.fleamarket.models.enums.Role;
import com.example.fleamarket.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);

        assertTrue(result);
        assertTrue(user.isActive());
        assertEquals(1, user.getRoles().size());
        assertEquals(Role.ROLE_USER, user.getRoles().iterator().next());
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateUserDuplicateEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(new User());

        boolean result = userService.createUser(user);

        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testListUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.list();

        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    public void testBanUser() {
        User user = new User();
        user.setId(1L);
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testChangeUserRoles() {
        User user = new User();
        user.setRoles(new HashSet<>(Collections.singletonList(Role.ROLE_USER)));
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_ADMIN", "on");


        when(userRepository.save(user)).thenReturn(user);

        userService.changeUserRoles(user, form);

        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        assertFalse(user.getRoles().contains(Role.ROLE_USER));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserByPrincipal() {
        User user = new User();
        user.setEmail("test@example.com");
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User result = userService.getUserByPrincipal(principal);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserByPrincipalNull() {
        User result = userService.getUserByPrincipal(null);

        assertNotNull(result);
        assertNull(result.getEmail());
    }
}