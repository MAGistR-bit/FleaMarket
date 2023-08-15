package com.example.fleamarket.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageTests {

    private Image image;

    @BeforeEach
    public void setUp() {
        image = new Image();
    }

    @Test
    public void testImageProperties() {
        assertNull(image.getName());
        assertNull(image.getOriginalFileName());
        assertNull(image.getSize());
        assertNull(image.getContentType());
        assertFalse(image.isPreviewImage());
        assertNull(image.getBytes());
        assertNull(image.getProduct());

        String name = "example.jpg";
        String originalFileName = "original_example.jpg";
        Long size = 1024L;
        String contentType = "image/jpeg";
        boolean previewImage = true;
        byte[] bytes = new byte[]{1, 2, 3};

        image.setName(name);
        image.setOriginalFileName(originalFileName);
        image.setSize(size);
        image.setContentType(contentType);
        image.setPreviewImage(previewImage);
        image.setBytes(bytes);

        assertEquals(name, image.getName());
        assertEquals(originalFileName, image.getOriginalFileName());
        assertEquals(size, image.getSize());
        assertEquals(contentType, image.getContentType());
        assertTrue(image.isPreviewImage());
        assertArrayEquals(bytes, image.getBytes());
    }

    @Test
    public void testProductAssociation() {
        Product product = new Product();
        image.setProduct(product);

        assertEquals(product, image.getProduct());
    }
}