package com.example.fleamarket.controller;

import com.example.fleamarket.models.Product;
import com.example.fleamarket.models.User;
import com.example.fleamarket.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProducts() {
        String title = "testTitle";
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        User user = new User();
        when(productService.listProducts(title)).thenReturn(Collections.emptyList());
        when(productService.getUserByPrincipal(principal)).thenReturn(user);

        String result = productController.products(title, principal, model);

        assertEquals("products", result);
        verify(model, times(1)).addAttribute("products", Collections.emptyList());
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("searchWord", title);
    }


    @Test
    public void testCreateProduct() throws IOException {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile file3 = mock(MultipartFile.class);
        Product product = new Product();
        Principal principal = mock(Principal.class);

        String result = productController.createProduct(file1, file2, file3, product, principal);

        assertEquals("redirect:/my/products", result);
        verify(productService, times(1)).saveProduct(principal, product, file1, file2, file3);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        Principal principal = mock(Principal.class);
        User user = new User();
        when(productService.getUserByPrincipal(principal)).thenReturn(user);

        String result = productController.deleteProduct(id, principal);

        assertEquals("redirect:/my/products", result);
        verify(productService, times(1)).deleteProduct(user, id);
    }


}