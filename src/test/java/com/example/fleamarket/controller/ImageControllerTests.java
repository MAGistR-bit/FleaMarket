package com.example.fleamarket.controller;

import com.example.fleamarket.models.Image;
import com.example.fleamarket.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageControllerTests {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetImageById() {
        Long id = 1L;
        Image image = new Image();
        image.setOriginalFileName("test.jpg");
        image.setContentType("image/jpeg");
        image.setSize(1024L);
        image.setBytes(new byte[]{});

        when(imageRepository.findById(id)).thenReturn(Optional.of(image));

        ResponseEntity<?> responseEntity = imageController.getImageById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("test.jpg", responseEntity.getHeaders().getFirst("fileName"));
        assertEquals(MediaType.IMAGE_JPEG, responseEntity.getHeaders().getContentType());
        assertEquals(1024L, responseEntity.getHeaders().getContentLength());
        assertEquals(InputStreamResource.class, responseEntity.getBody().getClass());

        verify(imageRepository, times(1)).findById(id);
    }


}