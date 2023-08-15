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
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdmin() {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);

        when(userService.list()).thenReturn(Collections.emptyList());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String viewName = adminController.admin(model, principal);

        assertEquals("admin", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(model, times(1)).addAttribute(eq("user"), any());
    }

    @Test
    public void testUserBan() {
        Long id = 1L;

        String viewName = adminController.userBan(id);

        assertEquals("redirect:/admin", viewName);
        verify(userService, times(1)).banUser(id);
    }


    @Test
    public void testUserEditPost() {
        User user = new User();
        user.setId(1L);
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_USER", "on");

        String viewName = adminController.userEdit(user, form);

        assertEquals("redirect:/admin", viewName);
        verify(userService, times(1)).changeUserRoles(user, form);
    }
}