package com.example.fleamarket.controller;

import com.example.fleamarket.models.User;
import com.example.fleamarket.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin() {
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String result = userController.login(principal, model);

        assertEquals("login", result);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    public void testProfile() {
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String result = userController.profile(principal, model);

        assertEquals("profile", result);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    public void testRegistration() {
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String result = userController.registration(principal, model);

        assertEquals("registration", result);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    public void testCreateUserSuccess() {
        User user = new User();
        Model model = mock(Model.class);
        when(userService.createUser(user)).thenReturn(true);

        String result = userController.createUser(user, model);

        assertEquals("redirect:/login", result);
        verify(model, never()).addAttribute(eq("errorMessage"), anyString());
    }

    @Test
    public void testCreateUserDuplicateEmail() {
        User user = new User();
        Model model = mock(Model.class);
        when(userService.createUser(user)).thenReturn(false);

        String result = userController.createUser(user, model);

        assertEquals("registration", result);
        verify(model, times(1)).addAttribute(eq("errorMessage"),
                eq("Пользователь с email: " + user.getEmail() + " уже существует"));
    }

}