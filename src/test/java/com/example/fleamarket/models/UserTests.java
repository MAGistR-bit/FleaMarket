package com.example.fleamarket.models;

import com.example.fleamarket.models.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTests {

    @Test
    public void testAdminRole() {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        assertFalse(user.isAdmin());

        roles.add(Role.ROLE_ADMIN);
        user.setRoles(roles);

        assertTrue(user.isAdmin());
    }

    @Test
    public void testAuthorities() {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        assertEquals(roles, user.getAuthorities());
    }

    @Test
    public void testUsername() {
        User user = new User();
        user.setEmail("test@example.com");

        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    public void testAccountNonExpired() {
        User user = new User();

        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        User user = new User();

        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        User user = new User();

        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled() {
        User user = new User();
        user.setActive(true);

        assertTrue(user.isEnabled());
    }

    @Test
    public void testDateOfCreatedInitialization() {
        User user = new User();
        assertNull(user.getDateOfCreated());

        user.init();
        assertNotNull(user.getDateOfCreated());
    }

    @Test
    public void testProductsListInitialization() {
        User user = new User();
        List<Product> products = user.getProducts();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}