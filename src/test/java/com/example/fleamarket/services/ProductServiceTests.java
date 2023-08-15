package com.example.fleamarket.services;

import com.example.fleamarket.models.Product;
import com.example.fleamarket.models.User;
import com.example.fleamarket.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.listProducts(null);

        assertNotNull(result);
        assertEquals(productList, result);
    }


    @Test
    public void testDeleteProduct() {
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setUser(user);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(user, 1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(product, result);
    }
}