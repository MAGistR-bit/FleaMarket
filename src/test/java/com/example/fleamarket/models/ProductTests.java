package com.example.fleamarket.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductTests {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testInit() {
        assertNull(product.getDateOfCreated());

        product.init();
        assertNotNull(product.getDateOfCreated());
    }

    @Test
    public void testAddImageToProduct() {
        Image image = new Image();
        product.addImageToProduct(image);

        assertTrue(product.getImages().contains(image));
        assertEquals(product, image.getProduct());
    }

    @Test
    public void testUserAssociation() {
        User user = new User();
        product.setUser(user);

        assertEquals(user, product.getUser());
    }
}